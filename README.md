# MKR-ANDROID-LIB-UI

#	Project Level Gradle
		repositories {
			maven {
				url "https://api.bitbucket.org/1.0/repositories/THEMKR/android-lib-ui/raw/releases"
				credentials {
					username 'THEMKR'
					password '<PASSWORD>'
				}
			}
		}

#	APP Level Gradle
		<!-- DEPENDENCY INCLUDE IN LIB -->
        implementation 'com.android.support:recyclerview-v7:28.0.0'
        implementation 'com.android.support:support-v4:28.0.0'
        implementation 'com.android.support:customtabs:28.0.0'
        
        <!-- SUPPORT MY BE INCLUDE -->
        implementation 'com.lory.library:ui:1.0.5'