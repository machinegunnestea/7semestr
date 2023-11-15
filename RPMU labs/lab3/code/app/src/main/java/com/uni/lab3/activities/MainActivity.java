package com.uni.lab3.activities;

import static com.uni.lab3.themes.Themes.*;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.uni.lab3.IO.productsWriter.ProductsWriter;
import com.uni.lab3.R;
import com.uni.lab3.fragments.DeleteAlertDialog;
import com.uni.lab3.fragments.FullProductInfoFragment;
import com.uni.lab3.fragments.ProductSelectorDialog;
import com.uni.lab3.model.Product;
import com.uni.lab3.model.ProductsRepository;
import com.uni.lab3.IO.productsReader.ProductsReader;
import com.uni.lab3.themes.Themes;
import com.uni.lab3.themes.ThemesUtils;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DeleteAlertDialog.DialogListener, ProductSelectorDialog.DialogListener {
    public enum SelectProductIdReasons {
        DELETE,
        UPDATE
    }

    public enum AddEditFormVariants {
        ADD,
        EDIT
    }

    public final static String ADD_EDIT_VARIANT = "add_edit_variant";
    public final static String PRODUCTS_REPOSITORY = "productsRepository";
    public final static String CURRENT_THEME = "currentTheme";
    public final static String REASON = "reason";
    public final static String PRODUCT = "product";
    public final static int CONSTRAINED_SEARCH_RESULT = 1;
    public final static int ADD_PRODUCT_RESULT = 2;
    public final static int EDIT_PRODUCT_RESULT = 3;

    private ProductsRepository productsRepository;
    private Themes currentTheme = GREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            productsRepository = (ProductsRepository) savedInstanceState.getSerializable(PRODUCTS_REPOSITORY);
            currentTheme = (Themes) savedInstanceState.getSerializable(CURRENT_THEME);
        }
        setTheme(ThemesUtils.getThemeId(currentTheme));
        setContentView(R.layout.activity_main);

        ListView productsListView = findViewById(R.id.productsListView);
        productsListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Fragment previousFragment = getFullInfoProductFragment();
            if (previousFragment != null) {
                removeFullProductInfoFragment(previousFragment);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable(PRODUCT, productsRepository.getByIndex(i));
            getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, FullProductInfoFragment.class, bundle, FullProductInfoFragment.FRAGMENT_TAG)
                .commit();
        });

        if (productsRepository != null) {
            setProductsInListView(productsRepository.getAll());
        } else {
            loadProducts();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.load_products: {
                loadProducts();
                return true;
            }
            case R.id.save_products: {
                saveProducts();
                return true;
            }
            case R.id.search:
                onSearchRequested();
                return true;
            case R.id.search_with_price: {
                Intent intent = new Intent(this, ConstrainedSearch.class);
                intent.putExtra(CURRENT_THEME, currentTheme);
                startActivityForResult(intent, CONSTRAINED_SEARCH_RESULT);
                return true;
            }
            case R.id.get_all: {
                applySearchQuery("");
                setMaxPriceQueryText("");
                return true;
            }
            // actions
            case R.id.create_product: {
                Intent intent = new Intent(this, AddEditForm.class);
                intent.putExtra(CURRENT_THEME, currentTheme);
                intent.putExtra(ADD_EDIT_VARIANT, AddEditFormVariants.ADD);
                startActivityForResult(intent, ADD_PRODUCT_RESULT);
                return true;
            }
            case R.id.update_product: {
                showProductSelectDialog(SelectProductIdReasons.UPDATE);
                return true;
            }
            case R.id.delete_product: {
                showProductSelectDialog(SelectProductIdReasons.DELETE);
                return true;
            }
            // themes
            case R.id.default_theme: {
                currentTheme = DEFAULT;
                recreate();
                return true;
            }
            case R.id.red_theme: {
                currentTheme = RED;
                recreate();
                return true;
            }
            case R.id.green_theme: {
                currentTheme = GREEN;
                recreate();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == CONSTRAINED_SEARCH_RESULT) {
            String name = data.getStringExtra("name");
            int maxPrice = data.getIntExtra("maxPrice", 0);
            applySearchWithPriceQuery(name, maxPrice);
        }

        if (data.getSerializableExtra(PRODUCT) == null) return;

        Product product = (Product) data.getSerializableExtra(PRODUCT);
        switch (requestCode) {
            case ADD_PRODUCT_RESULT: {
                productsRepository.add(product);
                break;
            }
            case EDIT_PRODUCT_RESULT: {
                productsRepository.update(product);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
        setProductsInListView(productsRepository.getAll());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(PRODUCTS_REPOSITORY, productsRepository);
        savedInstanceState.putSerializable(CURRENT_THEME, currentTheme);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            applySearchQuery(query.toLowerCase(Locale.ROOT));
        }
    }

    public void setProductsInListView(Product[] products) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, Arrays.stream(products).map(Product::toShortString).toArray(String[]::new));
        ListView productsListView = findViewById(R.id.productsListView);
        productsListView.setAdapter(arrayAdapter);
    }

    public Fragment getFullInfoProductFragment() {
        return getSupportFragmentManager().findFragmentByTag(FullProductInfoFragment.FRAGMENT_TAG);
    }

    public void removeFullProductInfoFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }

    public void applySearchQuery(String searchQuery) {
        setSearchQueryText(searchQuery);
        setProductsInListView(Arrays.stream(productsRepository.getAll()).filter(product -> product.getName().toLowerCase(Locale.ROOT).contains(searchQuery)).toArray(Product[]::new));
    }

    public void applySearchWithPriceQuery(String name, int maxPrice) {
        setSearchQueryText(name);
        setMaxPriceQueryText(Integer.toString(maxPrice));
        setProductsInListView(Arrays.stream(productsRepository.getAll()).filter(product ->
                        product.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))
                                && product.getPrice() <= maxPrice)
                .toArray(Product[]::new)
        );
    }

    public void setSearchQueryText(String text) {
        TextView searchQueryTextView = findViewById(R.id.searchQueryTextView);
        if (text.isEmpty()) {
            searchQueryTextView.setText("");
        } else {
            searchQueryTextView.setText(String.format("Name: %s", text));
        }
    }

    public void setMaxPriceQueryText(String text) {
        TextView maxPriceTextView = findViewById(R.id.maxPriceTextView);
        if (text.isEmpty()) {
            maxPriceTextView.setText("");
        } else {
            maxPriceTextView.setText(String.format("Max price: %s", text));
        }
    }

    private void loadProducts() {
        try {
            ProductsReader productsReader = new ProductsReader(
                    MainActivity.this,
                    new BufferedReader(new InputStreamReader(getAssets().open("products.json"))
                    ),
                    (products) -> {
                        productsRepository = new ProductsRepository(products);
                        setProductsInListView(products);
                    });
            productsReader.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProducts() {
        try {
            ProductsWriter productsWriter = new ProductsWriter(
                    MainActivity.this,
                    new BufferedWriter(new OutputStreamWriter(this.openFileOutput("products.json", MODE_PRIVATE))
                    ),
                    productsRepository.getAll(),
                    (result) -> {});
            productsWriter.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfirmDeleteProductId(int productId) {
        productsRepository.removeById(productId);
        setProductsInListView(productsRepository.getAll());
    }

    public void removeDeleteDialogFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment selectProductIdDialog = (DialogFragment) getSupportFragmentManager().findFragmentByTag(DeleteAlertDialog.SELECT_DIALOG);
        DialogFragment confirmationDialog = (DialogFragment) getSupportFragmentManager().findFragmentByTag(DeleteAlertDialog.DELETE_CONFIRMATION_DIALOG);
        if (selectProductIdDialog != null) {
            selectProductIdDialog.dismiss();
        }
        if (confirmationDialog != null) {
            confirmationDialog.dismiss();
        }
        ft.commit();
    }

    @Override
    public void onSelectProductId(int selectedProductId, SelectProductIdReasons reason) {
        switch (reason) {
            case UPDATE: {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                DialogFragment selectProductIdDialog = (DialogFragment) getSupportFragmentManager().findFragmentByTag(DeleteAlertDialog.SELECT_DIALOG);
                if (selectProductIdDialog != null) {
                    selectProductIdDialog.dismiss();
                }
                ft.commit();

                Intent intent = new Intent(this, AddEditForm.class);
                intent.putExtra(CURRENT_THEME, currentTheme);
                intent.putExtra(ADD_EDIT_VARIANT, AddEditFormVariants.EDIT);
                intent.putExtra(PRODUCT, productsRepository.getById(selectedProductId));
                startActivityForResult(intent, EDIT_PRODUCT_RESULT);
                break;
            }
            case DELETE: {
                DeleteAlertDialog deleteAlertDialog = new DeleteAlertDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(DeleteAlertDialog.PRODUCT_ID, selectedProductId);
                bundle.putString(DeleteAlertDialog.ALERT_TITLE, "Confirm deleting");
                bundle.putString(DeleteAlertDialog.ALERT_MESSAGE, "Delete product with id:");
                deleteAlertDialog.setArguments(bundle);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                deleteAlertDialog.show(ft, DeleteAlertDialog.DELETE_CONFIRMATION_DIALOG);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + reason);
        }
    }

    public void showProductSelectDialog(SelectProductIdReasons reason) {
        if (productsRepository.length() == 0) return;
        ProductSelectorDialog dialogFragment = new ProductSelectorDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(REASON, reason);
        bundle.putSerializable(ProductSelectorDialog.PRODUCT_IDS, Arrays.stream(productsRepository.getAll()).map(Product::getId).mapToInt(i -> i).toArray());
        dialogFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        dialogFragment.show(ft, ProductSelectorDialog.SELECT_DIALOG);
    }
}