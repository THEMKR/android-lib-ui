# MKR-ANDROID-LIB-UI

#	Project Level Gradle
		repositories {
			maven { url "https://api.bitbucket.org/2.0/repositories/THEMKR/android-libs/src/releases" }
		}

#	APP Level Gradle

    implementation 'com.lory.library:ui:2.0.1'
	
	<!-- DEPENDENCY INCLUDE IN LIB -->
    def core_version = "1.3.0"
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation "androidx.core:core:$core_version" // Java
    implementation "androidx.core:core-ktx:$core_version" // Kotlin
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation "androidx.viewpager2:viewpager2:1.0.0"
    implementation 'com.squareup.okhttp3:okhttp:3.14.3'
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.browser:browser:1.2.0'
