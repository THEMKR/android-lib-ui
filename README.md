# MKR-ANDROID-LIB-UI

#	Project Level Gradle
		repositories {
			maven {
				url 'https://dl.google.com/dl/android/maven2'
			}
		}

#	APP Level Gradle

        implementation 'com.github.THEMKR:android-lib-ui:1.0.0'
	
	<!-- DEPENDENCY INCLUDE IN LIB -->
	implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
	implementation 'androidx.core:core-ktx:1.0.2'
	implementation 'com.google.android.material:material:1.0.0-rc01'
	implementation 'androidx.appcompat:appcompat:1.0.0'
	implementation 'androidx.recyclerview:recyclerview:1.0.0'
	implementation 'androidx.legacy:legacy-support-v4:1.0.0'
	implementation 'androidx.browser:browser:1.0.0'
