plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.homeheirs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.homeheirs"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {


    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1") {
        exclude(module = "protobuf-lite")
    }

//    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))
//    implementation("com.google.firebase:firebase-firestore")
//    implementation("androidx.core:core-ktx:1.8.0")
//    implementation("androidx.appcompat:appcompat:1.5.0")
//    implementation("com.google.android.material:material:1.10.0")
//    implementation("com.google.firebase:firebase-storage:20.3.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//    androidTestImplementation ("com.androidx.test.espresso:espresso-contrib:3.5.1"){
//
//        exclude(module="com.google.firebase:protolite-well-known-types:18.0.0")
//        exclude(module="com.android.support:support-annotations:28.0.0")
//        exclude(module="com.android.support:support-v4:28.0.0")
//        exclude(module="com.android.support:recyclerview-v7:28.0.0")
//        exclude(module="com.android.support:support-v13:28.0.0")
//        exclude(module="com.android.support:design:28.0.0")
//
//
////        exclude module: 'support-annotations'
////
////
////        exclude module: 'support-v4'
////        exclude module: 'support-v13'
////        exclude module: 'recyclerview-v7'
////        exclude module: 'design'
//    }
//    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))
//    implementation("com.google.firebase:firebase-firestore")
//    implementation("androidx.core:core-ktx:1.8.0")
//    implementation("androidx.appcompat:appcompat:1.5.0")
//    implementation("com.google.android.material:material:1.10.0")
//    implementation("com.google.firebase:firebase-storage:20.3.0")
//    testImplementation("junit:junit:4.13.2")
////    //androidTestImplementation ("com.android.support.test.espresso:espresso-contrib:2.0")
//    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1"){
//        exclude(group="androidx.test.espresso:espresso-contrib:3.5.1", module="protobuf-lite")
//    }
//
//    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))
//
//   // implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation ("com.android.volley:volley:1.2.1")
//    implementation ("com.squareup.picasso:picasso:2.71828")
//    //implementation ("androidx.legacy:legacy-support-v4:1.0.0")
//    implementation ("androidx.room:room-runtime:2.6.0")
//    androidTestImplementation ("androidx.test:runner:1.5.2")
//    androidTestImplementation ("androidx.test:rules:1.5.0")
//    androidTestImplementation ("androidx.room:room-testing:2.6.0")
//    annotationProcessor ("androidx.room:room-compiler:2.6.0")
//
//
//
//
//
//
//    implementation("com.google.firebase:firebase-firestore:24.9.1")
//    implementation("androidx.core:core-ktx:1.12.0")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.10.0")
//    implementation("com.google.firebase:firebase-storage:20.3.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//
//
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//
//    androidTestImplementation("androidx.test:runner:1.4.0")
//    androidTestImplementation ("androidx.test:rules:1.4.0")
//    androidTestImplementation ("androidx.room:room-testing:2.4.2")








}