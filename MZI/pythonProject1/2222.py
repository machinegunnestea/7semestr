import binascii


S_BOXES = [
    (4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3),
    (14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9),
    (5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11),
    (7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3),
    (6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2),
    (4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14),
    (13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12),
    (1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12)
]


def gost_encrypt(key, data):
    # Convert the key to a byte array
    key_bytes = bytearray.fromhex(key)

    # Convert the data to a byte array
    data_bytes = bytearray(data.encode())

    # Feedback with cipher-block chaining (CBC)
    iv = bytearray(8)  # Initialization vector
    encrypted_data = bytearray()

    for i in range(len(data_bytes)):
        # Encrypt the current data byte
        encrypted_byte = data_bytes[i] ^ iv[i % 8] ^ key_bytes[i % len(key_bytes)]
        encrypted_byte = s_box_transform(encrypted_byte, i % 8)
        encrypted_data.append(encrypted_byte)

        # Update the initialization vector
        iv[i % 8] = encrypted_byte

    # Return the encrypted data as a byte array
    return encrypted_data


def gost_decrypt(key, data):
    # Convert the key to a byte array
    key_bytes = bytearray.fromhex(key)

    # Feedback with cipher-block chaining (CBC)
    iv = bytearray(8)  # Initialization vector
    decrypted_data = bytearray()

    for i in range(len(data)):
        # Decrypt the current data byte
        decrypted_byte = s_box_transform(data[i], i % 8)
        decrypted_byte = decrypted_byte ^ iv[i % 8] ^ key_bytes[i % len(key_bytes)]
        decrypted_data.append(decrypted_byte)

        # Update the initialization vector
        iv[i % 8] = data[i]

    # Return the decrypted data as a string
    return decrypted_data.decode()


def s_box_transform(byte, index):
    # Get the corresponding S-box
    s_box = S_BOXES[index]

    # Calculate the S-box indices
    row = (byte >> 4) & 0x0F
    col = byte & 0x0F

    # Get the value from the S-box
    return s_box[row * 16 + col]


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
