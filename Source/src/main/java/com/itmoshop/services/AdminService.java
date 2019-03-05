package com.itmoshop.services;

import com.itmoshop.data.Account;
import com.itmoshop.data.Book;
import com.itmoshop.data.ItemOrder;
import com.itmoshop.persistence.AccountDAO;
import com.itmoshop.persistence.BookDAO;
import com.itmoshop.persistence.ItemOrderDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class AdminService {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private ItemOrderDAO itemOrderDAO;

    private static final String IMG_FOLDER_PATH =
            File.separator + "resources" + File.separator + "images" + File.separator + "book_covers";

    public File getWebRootPath() {
        File classLoaderPath = null;
        try {
            classLoaderPath = new File(getClass().getClassLoader().getResource("/" + File.separator).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File webRootPath = classLoaderPath.getParentFile().getParentFile();
        return webRootPath;
    }

    @Transactional
    public Account findAccountById(long id) {
        return accountDAO.findAccountById(id);
    }

    @Transactional
    public Account findAccountByEmail(String email) {
        return accountDAO.findAccountByEmail(email);
    }

    @Transactional
    public Account saveAccount(Account account) {
        return accountDAO.saveAccount(account);
    }

    public ItemOrder findItemOrderById(long id) {
        return itemOrderDAO.findItemOrderById(id);
    }

    public void addSingleBookImgToServer(MultipartFile bookImgFile) {
        try {
            Path fullFilePath = Paths.get(getWebRootPath().toString() + IMG_FOLDER_PATH + File.separator + bookImgFile.getOriginalFilename());
            Files.write(fullFilePath, bookImgFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<Book> addArchiveContentsToServer(MultipartFile archive) {
        List<Book> addedBooks = new ArrayList<>();
        ZipInputStream zis = null;
        BufferedOutputStream bos = null;
        try {
            Path tempFile = Files.createTempFile("", "");
            Files.write(tempFile, archive.getBytes());
            zis = new ZipInputStream(Files.newInputStream(tempFile));
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {

                String fileName = zipEntry.getName();
                if (fileName.endsWith(".json")) {
                    Gson gson = new GsonBuilder().create();
                    JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(zis));
                    Book bookToAdd = gson.fromJson(jsonReader, Book.class);
                    bookToAdd = bookDAO.saveBook(bookToAdd);
                    addedBooks.add(bookToAdd);
                    continue;
                }

                Path folderPath = Paths.get(getWebRootPath().toString() + IMG_FOLDER_PATH);
                Path fullFilePath = Paths.get(folderPath.toString() + File.separator + fileName);
                bos = new BufferedOutputStream(new FileOutputStream(fullFilePath.toFile()));
                byte[] buffer = new byte[2097152];
                int pointer;
                while ((pointer = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, pointer);
                }
                bos.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                zis.close();
                bos.close();
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

        return addedBooks;
    }

    @Transactional
    public void deleteAccount(Account account) {
        accountDAO.deleteAccount(account);
    }

    @Transactional
    public int deleteAllAccountsExceptAdmin() {
        return accountDAO.deleteAllAccountsExceptAdmin();
    }

    @Transactional
    public int deleteAllBooks() {

        String imgFolderRealPath = getWebRootPath().toString() + File.separator + IMG_FOLDER_PATH;
        for (File file : new File(imgFolderRealPath).listFiles()) {
            file.delete();
        }

        return bookDAO.deleteAllBooks();
    }


    @Transactional
    public ItemOrder saveOrder(ItemOrder itemOrder) {
        return itemOrderDAO.saveItemOrder(itemOrder);
    }

    @Transactional
    public void deleteItemOrder(ItemOrder itemOrder) {
        itemOrderDAO.deleteItemOrder(itemOrder);
    }

    @Transactional
    public int deleteAllItemOrders() {
        return itemOrderDAO.deleteAllItemOrders();
    }

}
