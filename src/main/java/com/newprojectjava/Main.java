package com.newprojectjava;

import org.hibernate.Session;
import org.hibernate.query.Query;
import com.newprojectjava.entities.CategoryEntity;
import com.newprojectjava.utlis.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var sf = HibernateUtil.getSessionFactory();
        
        //     try(Session session = sf.openSession())
        //     {
        //         session.beginTransaction();
                
        //         CategoryEntity category = new CategoryEntity();
        //         category.setName("Одяг");
        //         category.setImage("1.jpg");
                
        //         session.save(category);
        //         session.getTransaction().commit();
        //     }
        //     sf.close();

        try (Session session = sf.openSession()) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. Display Categories");
                System.out.println("2. Add Category");
                System.out.println("3. Edit Category");
                System.out.println("4. Delete Category");
                System.out.println("5. Exit");
                System.out.print("Choose operation (1-5): ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                switch (choice) {
                    case 1:
                        displayCategories(session);
                        break;
                    case 2:
                        addCategory(session, scanner);
                        break;
                    case 3:
                        editCategory(session, scanner);
                        break;
                    case 4:
                        deleteCategory(session, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        sf.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                        break;
                }
            }
        }
    }

    private static void displayCategories(Session session) {
        Query<CategoryEntity> query = session.createQuery("FROM CategoryEntity", CategoryEntity.class);
        List<CategoryEntity> categories = query.list();
        System.out.println("-----------------");
        System.out.println("Categories:");
        for (CategoryEntity category : categories) {
            System.out.println(category.getId() + ". " + category.getName() + " - " + category.getImage());
        }
        System.out.println("-----------------");
    }

    private static void addCategory(Session session, Scanner scanner) {
        session.beginTransaction();
        System.out.println("-----------------");
        CategoryEntity category = new CategoryEntity();
        System.out.print("Enter category name: ");
        category.setName(scanner.nextLine());
        System.out.print("Enter category image: ");
        category.setImage(scanner.nextLine());

        session.save(category);
        session.getTransaction().commit();

        System.out.println("Category added successfully.");
        System.out.println("-----------------");
    }

    private static void editCategory(Session session, Scanner scanner) {
        displayCategories(session);
        System.out.println("-----------------");
        System.out.print("Enter the ID of the category to edit: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine(); 

        CategoryEntity category = session.get(CategoryEntity.class, categoryId);
        if (category != null) {
            System.out.print("Enter new category name: ");
            category.setName(scanner.nextLine());
            System.out.print("Enter new category image: ");
            category.setImage(scanner.nextLine());

            session.beginTransaction();
            session.update(category);
            session.getTransaction().commit();

            System.out.println("Category updated successfully.");
            System.out.println("-----------------");
        } else {
            System.out.println("Category not found.");
            System.out.println("-----------------");
        }
    }

    private static void deleteCategory(Session session, Scanner scanner) {
        displayCategories(session);
        System.out.println("-----------------");
        System.out.print("Enter the ID of the category to delete: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine(); 

        CategoryEntity category = session.get(CategoryEntity.class, categoryId);
        if (category != null) {
            session.beginTransaction();
            session.delete(category);
            session.getTransaction().commit();

            System.out.println("Category deleted successfully.");
            System.out.println("-----------------");
        } else {
            System.out.println("Category not found.");
            System.out.println("-----------------");
        }
    }
}