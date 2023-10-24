import binascii


def gost_encrypt(key, data):
    # Преобразование ключа в список байтов
    key_bytes = bytearray.fromhex(key)

    # Преобразование данных в список байтов
    data_bytes = bytearray(data.encode())

    # Гаммирование с обратной связью
    iv = bytearray(8)  # Инициализирующий вектор
    encrypted_data = bytearray()

    for i in range(len(data_bytes)):
        # Шифрование очередного байта данных
        encrypted_byte = data_bytes[i] ^ iv[i % 8] ^ key_bytes[i % len(key_bytes)]
        encrypted_data.append(encrypted_byte)

        # Обновление инициализирующего вектора
        iv[i % 8] = encrypted_byte

    # Возвращение зашифрованных данных в виде списка байтов
    return encrypted_data


def gost_decrypt(key, data):
    # Преобразование ключа в список байтов
    key_bytes = bytearray.fromhex(key)

    # Гаммирование с обратной связью (синхропосылка)
    iv = bytearray(8)  # Инициализирующий вектор
    decrypted_data = bytearray()

    for i in range(len(data)):
        # Расшифровка очередного байта данных
        decrypted_byte = data[i] ^ iv[i % 8] ^ key_bytes[i % len(key_bytes)]
        decrypted_data.append(decrypted_byte)

        # Обновление инициализирующего вектора
        iv[i % 8] = data[i]

    # Возвращение расшифрованных данных в виде строки
    return decrypted_data.decode()

def save_bytes_to_file(filename, data):
    with open(filename, "wb") as f:
        f.write(data)


def save_string_to_file(filename, data):
    with open(filename, "w") as f:
        f.write(data)


def read_bytes_from_file(filename):
    with open(filename, "rb") as f:
        return f.read()


def read_string_from_file(filename):
    with open(filename, "r") as f:
        return f.read()


def print_hex_and_text(data, label):
    hex_data = binascii.hexlify(data).decode()
    text_data = data.decode()
    print(f"{label} (Hex): {hex_data}")
    print(f"{label} (Text): {text_data}")


def main():
    key_file = "key.txt"
    data_file = "data.txt"
    encrypted_file = "encrypted.txt"
    decrypted_file = "decrypted.txt"

    # Чтение ключа из файла
    key = read_string_from_file(key_file)

    # Чтение данных из файла
    data = read_string_from_file(data_file)

    # Вывод исходных данных
    print("Original Key and Data:")
    print_hex_and_text(key.encode(), "Key")
    print_hex_and_text(data.encode(), "Data")
    print()

    # Шифрование данных
    encrypted_data = gost_encrypt(key, data)

    # Вывод зашифрованных данных
    print("Encrypted Data:")
    print_hex_and_text(encrypted_data, "Encrypted Data")
    print()

    # Сохранение зашифрованных данных в файл
    save_bytes_to_file(encrypted_file, encrypted_data)

    # Загрузка зашифрованных данных из файла
    encrypted_data = read_bytes_from_file(encrypted_file)

    # Дешифрование данных
    decrypted_data = gost_decrypt(key, encrypted_data)

    # Вывод расшифрованных данных
    print("Decrypted Data:")
    print_hex_and_text(decrypted_data.encode(), "Decrypted Data")
    print()

    # Сохранение расшифрованных данных в файл
    save_string_to_file(decrypted_file, decrypted_data)

    # Вывод успешного завершения операции
    print("Encryption and decryption completed successfully.")

    # Изменение ключа
    new_key = input("Enter a new key: ")
    key = new_key.strip()

    # Изменение данных
    new_data = input("Enter new data: ")
    data = new_data.strip()

    # Вывод обновленных данных
    print("Updated Key and Data:")
    print_hex_and_text(key.encode(), "Key")
    print_hex_and_text(data.encode(), "Data")
    print()

    # Шифрование обновленных данных
    encrypted_data = gost_encrypt(key, data)

    # Вывод зашифрованных данных
    print("Updated Encrypted Data:")
    print_hex_and_text(encrypted_data, "Encrypted Data")
    print()

    # Сохранение обновленных зашифрованных данных в файл
    save_bytes_to_file(encrypted_file, encrypted_data)

    # Загрузка обновленных зашифрованных данных из файла
    encrypted_data = read_bytes_from_file(encrypted_file)

    # Дешифрование обновленных данных
    decrypted_data = gost_decrypt(key, encrypted_data)

    # Вывод расшифрованных обновленных данных
    print("Updated Decrypted Data:")
    print_hex_and_text(decrypted_data.encode(), "Decrypted Data")
    print()

    # Сохранение расшифрованных обновленных данных в файл
    save_string_to_file(decrypted_file, decrypted_data)

    # Вывод успешного завершения операции
    print("Encryption and decryption of updated data completed successfully.")


if __name__ == "__main__":
    main()
