package com.codingchapters.isplugin

import com.intellij.ide.util.projectWizard.{SdkSettingsStep, SettingsStep}
import com.intellij.openapi.projectRoots.{JavaSdk, SdkTypeId}
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.Condition


class JdkStepSettings(settingsStep: SettingsStep,
                      chapterBuilder: SelectChapterBuilder,
                      sdkTypeIdFilter: Condition[_ >: SdkTypeId],
                      supportedJdkMajorVersion: Int)
  extends SdkSettingsStep(settingsStep, chapterBuilder, sdkTypeIdFilter)  {

  override def validate(): Boolean = {

    println("[validate] : JdkStepSettings")

    val sdk = myJdkComboBox.getSelectedJdk
    val sdkVersion = JavaSdk.getInstance().getVersion(sdk)
    val languageLevel = sdkVersion.getMaxLanguageLevel
    val feature = languageLevel.toJavaVersion.feature
    val isCorrectJdk = feature >= supportedJdkMajorVersion

    if(!isCorrectJdk){
      Messages.showErrorDialog("wrong jdk", "Error")
    }
    isCorrectJdk
  }
}

