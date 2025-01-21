
# Gazi BnB Backend

Bu proje, Gazi BnB tatil rezervasyon uygulamasının backend kısmını oluşturmaktadır. Kullanıcıların tatil yerlerini incelemesi, rezervasyon yapması ve favorilerine eklemesi gibi işlemlerin yönetimini sağlayan bir sistemdir.

## Kullanılan Teknolojiler
- **Backend:** Java (Spring Boot), PostgreSQL
- **Veritabanı:** PostgreSQL

## Özellikler
- Kullanıcıların rezervasyon işlemleri için backend desteği
- Veritabanı yönetimi ve kimlik doğrulama işlemleri
- API son noktalarının düzenlenmesi ve optimize edilmesi

## Başlangıç

Projeyi kendi bilgisayarınızda çalıştırmak için aşağıdaki adımları izleyebilirsiniz.

### Gereksinimler
- Java 21 veya daha yeni bir sürüm
- PostgreSQL veritabanı

### Projeyi Çalıştırmak

1. Proje dizininde terminali açın ve Spring Boot uygulamasını başlatın:

   ```bash
   ./mvnw spring-boot:run
   ```

2. Uygulama, varsayılan olarak `http://localhost:8080` adresinde çalışacaktır.

3. PostgreSQL veritabanı bağlantı ayarlarını `application.properties` dosyasına yapın:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/veritabani_adi
   spring.datasource.username=postgres
   spring.datasource.password=parolaniz
   ```

## Frontend Detayları

Bu backend, Gazi BnB tatil rezervasyon uygulamasının frontend kısmı ile entegre çalışmaktadır. Frontend detayları ve kurulum talimatlarına aşağıdaki bağlantıdan ulaşabilirsiniz:

> **Frontend için kaynak kodları ve daha fazla bilgiye ulaşmak için:**
> [Gazi BnB Frontend GitHub Repository](https://github.com/atillaertas1/gazi-bnb)
