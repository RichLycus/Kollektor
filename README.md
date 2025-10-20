# FormMaster - Aplikasi Input Data Sederhana

Aplikasi Android untuk input dan manajemen data sederhana dengan validasi, penyimpanan SQLite, dan tampilan data yang elegan.

## 📱 Fitur Aplikasi

### ✅ Input Data
- Form input dengan 5 field: Nama Lengkap, Email, Nomor Telepon, Alamat, Pesan (opsional)
- Validasi real-time untuk setiap field
- Material Design dengan TextInputLayout
- Gradient background yang menarik

### ✅ Validasi
- **Nama**: Minimal 2 karakter, tidak boleh kosong
- **Email**: Format email valid
- **Nomor Telepon**: 10-15 digit
- **Alamat**: Minimal 5 karakter, tidak boleh kosong
- **Pesan**: Opsional

### ✅ Penyimpanan Data
- SQLite database untuk persistent storage
- CRUD operations lengkap (Create, Read, Delete)
- Auto-generated timestamp untuk setiap entry

### ✅ Tampilan Data
- RecyclerView dengan CardView untuk list items
- Empty state yang informatif
- Format phone number otomatis (+62)
- Format tanggal Indonesia
- Animasi smooth pada card items

### ✅ Manajemen Data
- Delete confirmation dialog
- Refresh otomatis setelah delete
- Counter jumlah data tersimpan
- Back navigation dengan animasi

## 🏗️ Struktur Project

```
app/src/main/
├── java/com/example/formmaster/
│   ├── DatabaseHelper.kt       # SQLite database handler
│   ├── FormEntry.kt            # Data model & validation
│   ├── MainActivity.kt         # Form input activity
│   ├── DataListActivity.kt     # Data list display
│   └── FormAdapter.kt          # RecyclerView adapter
│
├── res/
│   ├── layout/
│   │   ├── activity_main.xml           # Main form layout
│   │   ├── activity_data_list.xml      # List display layout
│   │   └── item_form_entry.xml         # List item layout
│   │
│   ├── drawable/
│   │   ├── gradient_background.xml     # App gradient background
│   │   ├── button_gradient.xml         # Button gradient style
│   │   ├── button_outline.xml          # Outline button style
│   │   ├── badge_background.xml        # Badge background
│   │   ├── ic_email.xml                # Email icon
│   │   ├── ic_phone.xml                # Phone icon
│   │   ├── ic_location.xml             # Location icon
│   │   ├── ic_calendar.xml             # Calendar icon
│   │   └── ic_delete.xml               # Delete icon
│   │
│   └── values/
│       ├── colors.xml                  # Color palette
│       └── strings.xml                 # String resources
│
└── AndroidManifest.xml                 # App manifest
```

## 🎨 Design

### Color Palette
- **Primary Blue**: #2196F3
- **Primary Purple**: #9C27B0
- **Accent Light Blue**: #E3F2FD
- **Accent Light Purple**: #F3E5F5
- **Success Green**: #4CAF50
- **Error Red**: #F44336

### UI Components
- Material Design Components
- CardView dengan elevation
- Gradient backgrounds
- Vector icons
- Smooth animations

## 💾 Database

### Table: form_entries
| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Primary key (auto-increment) |
| name | TEXT | Nama lengkap (NOT NULL) |
| email | TEXT | Email address (NOT NULL) |
| phone | TEXT | Nomor telepon (NOT NULL) |
| address | TEXT | Alamat lengkap (NOT NULL) |
| message | TEXT | Pesan opsional |
| created_at | DATETIME | Timestamp (auto-generated) |

## 🧪 Testing

Unit tests tersedia di `app/src/test/java/com/example/formmaster/FormEntryTest.kt`:
- ✅ Valid form entry
- ✅ Invalid email format
- ✅ Empty name validation
- ✅ Invalid phone number
- ✅ Short address validation

## 📦 Dependencies

```gradle
implementation 'androidx.core:core-ktx:1.17.0'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.10.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'androidx.cardview:cardview:1.0.0'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
```

## 🚀 Cara Menjalankan

1. Clone repository ini
2. Buka project di Android Studio
3. Sync Gradle
4. Run pada emulator atau device (Min SDK 24)

## 📚 Tutorial

Aplikasi ini dibuat mengikuti tutorial lengkap dengan 11 BAB:
- BAB 1: Persiapan Project
- BAB 2: Setup Database
- BAB 3: Model Data
- BAB 4: Design Layout
- BAB 5: MainActivity (Form Input)
- BAB 6: DataListActivity (Tampilan Data)
- BAB 7: RecyclerView Adapter
- BAB 8: Resources & Styling
- BAB 9: Testing & Debugging
- BAB 10: Tips & Best Practices
- BAB 11: Kesimpulan & Langkah Selanjutnya

## ✨ Fitur Lanjutan (Future)

Ide pengembangan untuk masa depan:
- [ ] Edit data
- [ ] Search/Filter
- [ ] Export to CSV/PDF
- [ ] Backup & Restore
- [ ] Dark Mode
- [ ] Internationalization (i18n)
- [ ] Cloud sync

## 📄 License

Educational project - FormMaster © 2025

---
**Dibuat dengan ❤️ mengikuti tutorial Android Kotlin lengkap**
