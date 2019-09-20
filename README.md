# MKR-ANDROID-LIB-UI

#	Project Level Gradle
		repositories {
			maven { url "https://api.bitbucket.org/2.0/repositories/THEMKR/android-libs/src/releases" }
		}

#	APP Level Gradle

    implementation 'com.lory.library:ui:1.0.0'
	
	<!-- DEPENDENCY INCLUDE IN LIB -->
	implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    implementation "androidx.core:core:$core_version" // Java
    implementation "androidx.core:core-ktx:$core_version" // Kotlin
    implementation 'com.google.android.material:material:1.0.0-rc01'
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.browser:browser:1.0.0'
