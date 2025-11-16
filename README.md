# 🍲 World Cuisine (Dünya Mutfakları)

World Cuisine, [TheMealDB API](https://www.themealdb.com/api.php)'sini kullanarak kullanıcılara çeşitli dünya mutfaklarından yemek tariflerini keşfetme, detaylarını inceleme ve favori yemeklerini kaydetme imkanı sunan bir Android uygulamasıdır.

Bu proje, modern Android geliştirme prensipleri olan **Clean Architecture (Temiz Mimari)**, **MVVM** (MVI-benzeri tekil state akışı ile), **Jetpack Compose** ve **Hilt** gibi teknolojiler kullanılarak geliştirilmiştir.

## 📱 Ekran Görüntüleri

| Mutfaklar Listesi | Yemek Listesi | Yemek Detayı | Kaydedilenler |
| :---: | :---: | :---: | :---: |
| <img width="200" alt="Mutfaklar Ekranı" src="https://github.com/user-attachments/assets/7e20b257-7ee4-4f45-9814-d708bddf9d8a" /> | <img width="200" alt="Yemek Listesi Ekranı" src="https://github.com/user-attachments/assets/278dcde2-f930-411a-af9d-3dbe86b773f5" /> | <img width="200" alt="Yemek Detayı Ekranı" src="https://github.com/user-attachments/assets/41e593e0-2bae-4218-9b64-2473bb35e6d9" /> | <img width="200" alt="Kaydedilenler Ekranı" src="https://github.com/user-attachments/assets/da578118-03d0-4c54-83f2-dd7ef2bcd3d1" /> |

## ✨ Özellikler

* **Mutfakları Keşfet:** "Turkish", "Italian", "Japanese" gibi dünya mutfaklarını listeleyin.
* **Yemekleri Listele:** Seçilen bir mutfağa (veya ülkeye) ait tüm yemekleri görün.
* **Detaylı Tarif:** Yemeklerin detaylı tariflerine, malzemelerine ve görsellerine ulaşın.
* **Favorilere Ekleme:** Yemek detay ekranından beğendiğiniz yemekleri favorilerinize kaydedin.
* **Favorileri Görüntüleme:** Ayrı bir ekranda tüm favori yemeklerinizi listeleyin.
* **Favorilerden Çıkarma:** Favoriler ekranından dilediğiniz yemeği listenizden kaldırın.

---

## 🛠️ Mimari ve Kullanılan Teknolojiler

Bu proje, katmanlı, ölçeklenebilir ve bakımı kolay bir yapı olan **Clean Architecture (Temiz Mimari)** prensiplerine dayanmaktadır. Veri akışı, UI katmanından veri katmanına doğru tek yönlüdür (UDF - Unidirectional Data Flow).

`UI Katmanı -> ViewModel -> UseCase (İş Mantığı) -> Repository (Depo) -> Data Katmanı (API/Room)`

### 📚 Kullanılan Kütüphaneler ve Teknolojiler

* **Arayüz (UI):** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Modern, deklaratif UI kütüphanesi)
* **Mimari Desen:** **MVVM** (Model-View-ViewModel) / MVI-benzeri tekil `State` yönetimi
* **Bağımlılık Enjeksiyonu (DI):** [Hilt](https://dagger.dev/hilt/) (Android için bağımlılık yönetimi)
* **Asenkron Programlama:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html) (Reaktif ve asenkron veri akışları için)
* **Navigasyon:** [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) (Composable'lar arası geçiş yönetimi)
* **Yerel Veritabanı (Cache):** [Room](https://developer.android.com/jetpack/androidx/releases/room) (Favori yemeklerin kalıcı olarak saklanması için)
* **Ağ (Network):** [Retrofit 2](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson) (API'den veri çekmek ve JSON ayrıştırmak için)
* **Görsel Yükleme:** [Coil](https://coil-kt.github.io/coil/) (Coroutine tabanlı görsel yükleme kütüphanesi)
* **Yaşam Döngüsü (Lifecycle):** `ViewModel` & `SavedStateHandle` (ViewModel yaşam döngüsü ve navigasyon argümanlarını yönetmek için)
* **İkonlar:** `androidx.compose.material:material-icons-extended`

---

## 🚀 Kurulum

Projeyi yerel makinenizde çalıştırmak için:

1.  Bu depoyu (repository) klonlayın:
    ```bash
    git clone [https://github.com/Mertisler/worldcuisine.git](https://github.com/Mertisler/worldcuisine.git)
    ```
2.  Projeyi Android Studio (Hedgehog veya daha yeni bir sürüm) ile açın.
3.  Gerekli Gradle bağımlılıklarının indirilmesi için projeyi senkronize edin (**Sync Project**).
4.  Uygulamayı bir emülatör veya fiziksel bir cihaz üzerinde çalıştırın (`▶`).

---

## 👤 Oluşturan

**Mert İşler**
