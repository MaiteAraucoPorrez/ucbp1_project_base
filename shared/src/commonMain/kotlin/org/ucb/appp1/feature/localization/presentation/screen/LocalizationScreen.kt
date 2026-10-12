package org.ucb.appp1.feature.localization.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import appp1.shared.generated.resources.Res
import appp1.shared.generated.resources.app_name
import appp1.shared.generated.resources.btn_finish
import appp1.shared.generated.resources.btn_next
import appp1.shared.generated.resources.btn_skip
import appp1.shared.generated.resources.loc_description
import appp1.shared.generated.resources.loc_hint
import appp1.shared.generated.resources.loc_title
import appp1.shared.generated.resources.loc_welcome
import org.jetbrains.compose.resources.stringResource

/**
 * Pantalla de ejemplo con textos que vienen de Localise.biz.
 * Compose Multiplatform elige solo el idioma según el idioma del dispositivo
 * (values/, values-es/, values-fr/, values-ca/).
 */
@Composable
fun LocalizationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(stringResource(Res.string.loc_title), style = MaterialTheme.typography.headlineSmall)
        Text(stringResource(Res.string.app_name), style = MaterialTheme.typography.titleLarge)
        Text(stringResource(Res.string.loc_welcome), style = MaterialTheme.typography.titleMedium)
        Text(stringResource(Res.string.loc_description))
        Text(stringResource(Res.string.loc_hint), style = MaterialTheme.typography.bodySmall)

        // Botones del onboarding (pizarra: omitir / siguiente / finalizar)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = {}) { Text(stringResource(Res.string.btn_skip)) }
            Button(onClick = {}) { Text(stringResource(Res.string.btn_next)) }
            Button(onClick = {}) { Text(stringResource(Res.string.btn_finish)) }
        }
    }
}
