# Student Budget Tracker - Android App

A mobile application for students to manage daily expenses, track spending by category, and stay within budget. Built with Jetpack Compose and Material Design 3.

## Features

- User registration and login system
- Dashboard with budget overview and progress bar
- Add expenses with camera or gallery photos for receipts
- View, edit, and delete expenses
- Statistics with pie chart showing spending by category
- Offline support with Room Database
- Beautiful gradient UI with Material Design 3

## Technology Stack

- **Kotlin** with **Jetpack Compose**
- **Room Database** for local storage
- **Navigation Compose** for navigation
- **YCharts** for pie charts
- **Coil** for image loading
- **Material Design 3** UI

## Setup Instructions

### Step 1: Update build.gradle (App Level)

Add to plugins section:
```
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}
```

### Step 2: Add Dependencies

```
dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.3")
    
    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.7.5")
    
    // Charts
    implementation("co.yml:ycharts:2.1.0")
    
    // Database
    implementation("androidx.sqlite:sqlite:2.3.1")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    
    // Image Loading
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
```

### Step 3: Update AndroidManifest.xml

Add permissions and FileProvider:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-feature android:name="android.hardware.camera" android:required="false" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.StudentBudgetTracker">
        
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
        
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.StudentBudgetTracker">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

### Step 4: Create file_paths.xml

Create `app/src/main/res/xml/file_paths.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-files-path name="my_images" path="/" />
    <external-path name="external_files" path="." />
</paths>
```

### Step 5: Project Structure

Organize code into packages:

```
com.example.studentbudgettracker/
├── MainActivity.kt
├── Navigation.kt
├── data/
│   ├── User.kt
│   ├── Expense.kt
│   ├── AppDatabase.kt
│   ├── UserDao.kt
│   └── ExpenseDao.kt
├── screens/
│   ├── SplashScreen.kt
│   ├── LoginScreen.kt
│   ├── RegisterScreen.kt
│   ├── DashboardScreen.kt
│   ├── AddExpenseScreen.kt
│   ├── EditExpenseScreen.kt
│   ├── ExpenseListScreen.kt
│   └── StatisticsScreen.kt
└── ui/theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

### Step 6: Sync and Build

Click "Sync Now" in Android Studio, then build and run the app.

## How It Works

### Database Layer
- **Room Database** stores users and expenses locally
- **User Table**: id, username, password, email, weeklyBudget
- **Expense Table**: id, userId, amount, category, description, date, receiptPath

### App Flow
1. **Splash Screen** - Shows logo with animation, auto-navigates to login
2. **Register/Login** - User creates account or logs in
3. **Dashboard** - Shows budget summary, total spent, remaining amount
4. **Add Expense** - Enter amount, category, description, add receipt photo
5. **Expense List** - View all expenses, edit or delete any expense
6. **Statistics** - View pie chart of spending by category

### Camera Integration
- Uses Android camera API with FileProvider
- Supports both camera capture and gallery selection
- Stores photos in external files directory
- Permission handling for Android 13+

### Categories
Food, Transport, Books, Social, Utilities, Healthcare, Other

## Color Scheme

- **Primary Blue**: #2196F3
- **Purple**: #9C27B0
- **Orange**: #FF9800
- **Green**: #4CAF50
- **Red**: #F44336

## Permissions

- **CAMERA** - Capture receipt photos
- **READ_EXTERNAL_STORAGE** - Gallery access (Android 12 and below)
- **READ_MEDIA_IMAGES** - Gallery access (Android 13+)

## Testing Checklist

- Register new user
- Login with credentials
- Add expense with camera photo
- Add expense with gallery photo
- View all expenses
- Edit an expense
- Delete an expense
- View statistics chart
- Logout and login again

## Troubleshooting

**Build Issues**
- Add `id("kotlin-kapt")` to plugins if Room fails to compile
- Clean and rebuild project if dependencies fail

**Camera Not Working**
- Grant camera permission in device settings
- Test on physical device with camera

**Images Not Loading**
- Verify Coil dependency is added
- Check FileProvider configuration in manifest

**Database Issues**
- Verify Room dependencies with kapt
- Check all @Entity and @Dao annotations

## Requirements

- Minimum SDK: 26 (Android 8.0)
- Target SDK: 36
- Kotlin: 1.9.0+
- Android Studio: Hedgehog or later

## Notes

- All data stored locally on device
- No internet connection required
- Photos stored in app external files directory
- Session persists using SharedPreferences
- For production, add password encryption and backup features