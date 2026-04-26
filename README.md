# 🛒 Best Store - Spring Boot Product Management App

## 📌 Overview

**Best Store** is a simple Spring Boot web application that allows users to manage products.
It supports creating, viewing, and storing product details along with image uploads.

---

## 🚀 Features

* ✅ Create new products
* ✅ Upload and store product images
* ✅ View all products in a table
* ✅ Server-side validation using Jakarta Validation
* ✅ Image preview in product list
* ✅ Clean UI using Bootstrap

---

## 🛠️ Tech Stack

* **Backend:** Spring Boot, Spring MVC
* **Frontend:** Thymeleaf, HTML, Bootstrap
* **Database:** JPA / Hibernate
* **Build Tool:** Maven
* **Language:** Java

---

## 📂 Project Structure

```
src/main/java/com/spring/in/
│
├── controller/
│   └── ProductController.java
│
├── models/
│   ├── Product.java
│   └── ProductDto.java
│
├── services/
│   └── ProductsRepository.java
│
src/main/resources/
│
├── templates/products/
│   ├── index.html
│   └── CreateProduct.html
│
└── static/images/   (uploaded images stored here)
```

---

## ⚙️ How It Works

### 📝 Create Product

* Fill product details (name, brand, category, price, description)
* Upload image
* Form submits to `/products/create`
* Image is saved in:

  ```
  src/main/resources/static/images/
  ```
* Product data is stored in database

---

### 📋 View Products

* Access: `/products`
* Displays:

  * Product details
  * Uploaded image
  * Created date

---

## 🔐 Validation Rules

* Name → Required
* Brand → Required
* Category → Required
* Price → Must be ≥ 0
* Description → 10 to 2000 characters
* Image → Required

---

## ▶️ Run the Project

### 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/best-store.git
cd best-store
```

### 2️⃣ Run Application

```bash
mvn spring-boot:run
```

### 3️⃣ Open in Browser

```
http://localhost:8080/products
```

---

## 📸 Image Handling

* Images are stored locally in:

  ```
  static/images/
  ```
* Displayed using:

```html
<img th:src="@{'/images/' + ${product.imageFileName}}" />
```

---

## ⚠️ Notes

* Ensure `multipart/form-data` is enabled in form
* Folder `static/images/` must exist or will be created automatically
* File names are timestamp-based to avoid duplicates

---

## 🔮 Future Improvements

* ✏️ Edit Product
* 🗑️ Delete Product
* ☁️ Cloud image storage (AWS / Cloudinary)
* 🔍 Search & filter products
* 🔐 Authentication (login system)

---

## 👨‍💻 Author

**Prasad Chauhan**

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!

---

If you want, I can also:

* Add **screenshots section**
* Create **GitHub repo description + tags**
* Add **Docker setup**

Just tell 👍

## 📸 **1. Screenshots Section (add to README)**

## 📸 Screenshots

### ➕ Create Product Page

![Create Product](screenshots/create-product.png)

### 📋 Product List Page

![Product List](screenshots/product-list.png)

### 📁 Folder structure for screenshots

```
screenshots/
├── create-product.png
├── product-list.png
```

👉 Just take screenshots from your app and place them in this folder.

---

## 🧾 **2. GitHub Repo Description + Tags**

### 🔹 Repo Description

```
Spring Boot Product Management App with Image Upload, Thymeleaf UI, and CRUD operations
```

### 🔹 Topics / Tags (add in GitHub)

```
spring-boot
thymeleaf
java
crud
file-upload
bootstrap
spring-mvc
hibernate
jpa
web-application
```

---

## 🐳 **3. Docker Setup (Complete)**

### 📄 **Dockerfile**

Create a file named `Dockerfile` in root:

```dockerfile id="u8xk2p"
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 📄 **.dockerignore**

```id="2a1g2m"
target/
node_modules/
.git/
.gitignore
README.md
```

---

### ⚙️ Build JAR first

```bash id="c8d2s9"
mvn clean package
```

---

### 🏗️ Build Docker Image

```bash id="7m5z1p"
docker build -t best-store-app .
```

---

### ▶️ Run Container

```bash id="5g1h9k"
docker run -p 8080:8080 best-store-app
```

---

### 🌐 Open App

```
http://localhost:8080/products
```

---

## 🚀 Optional (Docker Compose)

If you later add DB (MySQL), use this:

```yaml id="9k3s2d"
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
```
