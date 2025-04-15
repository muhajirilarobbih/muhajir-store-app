# MuhajirStoreApp

**MuhajirStoreApp** adalah aplikasi toko online sederhana yang memungkinkan pengguna untuk login, melihat daftar produk, mengelola keranjang belanja, dan melihat profil mereka. Aplikasi ini dibangun dengan Jetpack Compose menggunakan Kotlin, mengikuti pola desain MVVM, dan menggunakan berbagai teknologi modern untuk memberikan pengalaman pengguna yang optimal.

## Fitur

1. **Halaman Login**
    - Memuat kolom untuk username dan password.
    - Menangani kasus positif (login berhasil) dan negatif (username/password salah) dengan pesan error.

2. **Halaman Utama (HomeScreen)**
    - **Daftar Produk**: Menampilkan produk dalam bentuk grid menggunakan data dari API.
    - **Daftar Kategori**: Menampilkan kategori produk dalam bentuk tab.
    - **Ikon Keranjang**: Mengarahkan pengguna ke halaman keranjang.
    - **Ikon Profil**: Menampilkan profil pengguna dalam bentuk bottom sheet.

3. **Filter Kategori**
    - Ketika kategori diklik, produk akan difilter dan ditampilkan berdasarkan kategori yang dipilih.

4. **Halaman Detail Produk**
    - Menampilkan informasi lengkap produk (gambar, nama, harga, deskripsi, dll.).
    - Tombol "Add to Bag" untuk menambahkan produk ke keranjang dengan alert sukses.

5. **Halaman Keranjang (CartScreen)**
    - Menampilkan daftar produk di keranjang beserta jumlahnya.
    - Fitur untuk mengubah jumlah produk atau menghapus produk dari keranjang.
    - Ringkasan pesanan (total harga).
    - Tombol checkout untuk melanjutkan ke halaman pembayaran.

6. **Halaman Profil (Bottom Sheet)**
    - Menampilkan informasi pengguna (username, email) dalam bottom sheet.
    - Tombol logout untuk keluar dari aplikasi dan menghapus semua data lokal.

7. **Handling Positif dan Negatif**
    - **Positif**: Login berhasil, produk berhasil ditambahkan ke keranjang, checkout sukses, dll.
    - **Negatif**: Login gagal (pesan error), jaringan bermasalah (pesan error), keranjang kosong, dll.

## Tech Stack

- **Bahasa Pemrograman**: Kotlin
- **Networking Library**: Retrofit (untuk mengambil data dari API)
- **Endpoint API**: [Fake Store API](https://fakestoreapi.com)
- **UI Framework**: Jetpack Compose (untuk antarmuka pengguna yang modern dan responsif)
- **Design Pattern**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt (untuk injeksi dependensi)
- **Local Storage**: Room (untuk menyimpan data keranjang dan informasi login)
- **UI/UX**: Desain menarik dengan ikon kustom, warna yang konsisten, dan animasi sederhana (efek kilau bintang pada ikon aplikasi)

## Arsitektur Proyek

Aplikasi ini mengikuti pola desain **MVVM** untuk memisahkan logika bisnis dari UI. Struktur utama proyek:

- **Model**: Berisi data class (`Product`, `CartItem`, `LoggedInUser`) dan logika terkait data (API, database).
- **View**: UI dibangun menggunakan Jetpack Compose (`LoginScreen`, `HomeScreen`, `ProductDetailScreen`, `CartScreen`, dll.).
- **ViewModel**: Menangani logika bisnis dan berkomunikasi antara UI dan data (`LoginViewModel`, `ProductViewModel`, `CartViewModel`).

### Struktur Folder
```
com.example.muhajirstoreapp/
├── data/
│   ├── api/                # Retrofit API service
│   ├── local/              # Room database dan DAO
│   └── repository/         # Repository untuk mengelola data
├── model/                  # Data class (Product, CartItem, LoggedInUser)
├── di/                     # Dependency Injection (Hilt modules)
├── ui/
│   ├── login/              # LoginScreen dan komponen terkait
│   ├── home/               # HomeScreen dan komponen terkait
│   ├── productdetail/      # ProductDetailScreen
│   ├── cart/               # CartScreen
│   └── payment/            # PaymentScreen
└── MainActivity.kt         # Entry point aplikasi
```


## Image APP
![Ikon Aplikasi MuhajirStoreApp](https://github.com/muhajirilarobbih/muhajir-store-app/blob/main/asset/splasScreen.png)
![Ikon Aplikasi MuhajirStoreApp]((https://github.com/muhajirilarobbih/muhajir-store-app/blob/main/asset/login.png))
![Ikon Aplikasi MuhajirStoreApp]((https://github.com/muhajirilarobbih/muhajir-store-app/blob/main/asset/product.png))
![Ikon Aplikasi MuhajirStoreApp]((https://github.com/muhajirilarobbih/muhajir-store-app/blob/main/asset/profile.png))
![Ikon Aplikasi MuhajirStoreApp]((https://github.com/muhajirilarobbih/muhajir-store-app/blob/main/asset/detailProduct.png))
![Ikon Aplikasi MuhajirStoreApp]((https://github.com/muhajirilarobbih/muhajir-store-app/blob/main/asset/cart.png))
![Ikon Aplikasi MuhajirStoreApp]((https://github.com/muhajirilarobbih/muhajir-store-app/blob/main/asset/cartPreview.png))


## Cara Menjalankan Aplikasi

### Prasyarat
- Android Studio (versi terbaru direkomendasikan)
- Kotlin Plugin
- Perangkat Android atau Emulator (API 21 atau lebih tinggi)

### Langkah-Langkah
1. **Clone Repository**
   ```bash
   git clone <repository-url>
   ```

2. **Buka Proyek di Android Studio**
    - Buka Android Studio, lalu pilih **Open an existing project**.
    - Arahkan ke folder proyek yang telah di-clone.

3. **Sinkronkan Proyek**
    - Klik **Sync Project with Gradle Files** untuk mengunduh dependensi.

4. **Jalankan Aplikasi**
    - Pilih perangkat/emulator dari daftar di Android Studio.
    - Klik **Run** untuk menjalankan aplikasi.

5. **Login**
    - Gunakan kredensial berikut untuk login (simulasi):
        - Username: `user`
        - Password: `pass`

### Catatan
- Pastikan perangkat/emulator terhubung ke internet karena aplikasi mengambil data produk dari API [Fake Store API](https://fakestoreapi.com).
- Jika Anda mengalami error terkait database (misalnya, "no such table"), hapus data aplikasi atau uninstall aplikasi terlebih dahulu untuk membuat ulang database Room.

## Dependensi

Berikut adalah dependensi utama yang digunakan dalam proyek ini:

```kotlin
dependencies {
    // Jetpack Compose
    implementation "androidx.compose.ui:ui:1.5.4"
    implementation "androidx.compose.material3:material3:1.2.1"
    implementation "androidx.compose.runtime:runtime-livedata:1.5.4"
    implementation "androidx.activity:activity-compose:1.8.2"
    
    // Retrofit
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    
    // Room
    implementation "androidx.room:room-runtime:2.6.1"
    implementation "androidx.room:room-ktx:2.6.1"
    kapt "androidx.room:room-compiler:2.6.1"
    
    // Hilt
    implementation "com.google.dagger:hilt-android:2.48"
    kapt "com.google.dagger:hilt-compiler:2.48"
    
    // Coil (untuk memuat gambar)
    implementation "io.coil-kt:coil-compose:2.4.0"
    
    // Navigation Compose
    implementation "androidx.navigation:navigation-compose:2.7.6"
    
    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    
    // Serialization (untuk navigasi dengan data kompleks)
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0"
}
```

## Desain UI/UX

Aplikasi ini dirancang dengan fokus pada UI/UX yang menarik:
- **Warna**: Menggunakan palet warna pastel (contoh: `Color(0xFFF5F5F5)` untuk latar belakang, `Color(0xFFFFA500)` untuk aksen).
- **Ikon Kustom**: Ikon aplikasi diganti dengan ikon toko yang menarik dengan efek kilau bintang.
- **Animasi**: Efek kilau bintang pada ikon aplikasi dan transisi halus antar layar menggunakan Jetpack Compose Navigation.
- **Layout Responsif**: Menggunakan grid untuk daftar produk dan tab untuk kategori agar mudah dinavigasi.

## Ikon Aplikasi

Ikon aplikasi telah diganti dengan ikon toko yang memiliki efek kilau bintang untuk memberikan kesan menarik. Ikon ini ditempatkan di folder `res/drawable`:

- **Nama File**: `ic_launcher.png` (untuk berbagai resolusi di folder `mipmap`).
- **Efek Kilau**: Ditambahkan bintang-bintang kecil pada ikon untuk memberikan efek berkilau.

Untuk mengganti ikon aplikasi:
1. Simpan ikon baru di folder `app/src/main/res/mipmap` (dengan nama `ic_launcher` untuk berbagai resolusi: `mipmap-mdpi`, `mipmap-hdpi`, dll.).
2. Pastikan file `AndroidManifest.xml` menggunakan ikon yang benar:
   ```xml
   android:icon="@mipmap/ic_launcher"
   ```

## Handling Positif dan Negatif

Aplikasi ini menangani kasus positif dan negatif dengan baik:
- **Login**:
    - **Positif**: Login berhasil dengan kredensial yang benar, pengguna diarahkan ke halaman utama.
    - **Negatif**: Kredensial salah, menampilkan pesan error "Invalid username or password".
- **Daftar Produk**:
    - **Positif**: Produk berhasil dimuat dari API, ditampilkan dalam grid.
    - **Negatif**: Gagal memuat data (misalnya, tidak ada koneksi internet), menampilkan pesan error.
- **Keranjang**:
    - **Positif**: Produk berhasil ditambahkan, jumlah dapat diubah, checkout berhasil.
    - **Negatif**: Keranjang kosong, menampilkan pesan "No items in cart".
- **Profil**:
    - **Positif**: Informasi pengguna ditampilkan dengan benar.
    - **Negatif**: Pengguna belum login, menampilkan pesan "User not logged in".

## Tambahan

- **Efek Visual**: Ikon aplikasi memiliki efek kilau bintang untuk meningkatkan daya tarik visual.
- **Room Database**: Menyimpan data keranjang dan informasi login secara lokal untuk mendukung penggunaan offline (terbatas).
- **Logout**: Menghapus semua data lokal (keranjang dan data login) saat pengguna logout.

## Kontribusi

Jika Anda ingin berkontribusi pada proyek ini:
1. Fork repository ini.
2. Buat branch baru (`git checkout -b feature/nama-fitur`).
3. Commit perubahan Anda (`git commit -m "Menambahkan fitur X"`).
4. Push ke branch Anda (`git push origin feature/nama-fitur`).
5. Buat Pull Request.

## Lisensi

Proyek ini dilisensikan di bawah [MIT License](LICENSE).

## Kontak

Jika Anda memiliki pertanyaan atau saran, silakan hubungi saya melalui email: [muhajirilarobbih@gmail.com](mailto:muhajirilarobbih@gmail.com).
atau no WA : 082120377243

---

Itulah file README yang mencakup semua kriteria yang Anda berikan. File ini memberikan gambaran lengkap tentang proyek, fitur, tech stack, cara menjalankan aplikasi, dan informasi tambahan seperti desain UI/UX dan handling kasus positif/negatif. Jika Anda ingin menambahkan detail lebih lanjut atau mengubah formatnya, beri tahu saya!