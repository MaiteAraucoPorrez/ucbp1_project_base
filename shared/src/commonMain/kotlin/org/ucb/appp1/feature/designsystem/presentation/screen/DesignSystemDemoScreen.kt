package org.ucb.appp1.feature.designsystem.presentation.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import edu.ucb.ds.components.buttons.PrimaryButton
import edu.ucb.ds.components.buttons.SecondaryButton
import edu.ucb.ds.components.cards.AppCard
import edu.ucb.ds.components.label.AppBody
import edu.ucb.ds.components.label.AppHeading
import edu.ucb.ds.components.label.AppLabel
import edu.ucb.ds.components.list.AppList
import edu.ucb.ds.components.list.AppListItem
import edu.ucb.ds.components.surfaces.AppSurface
import edu.ucb.ds.components.textfields.AppTextField
import edu.ucb.ds.icons.UserIcon
import edu.ucb.ds.theme.MyProjectTheme

/**
 * Pantalla de prueba: usa SOLO componentes de la librería edu.ucb.ds:shared (Design System).
 * Si compila y se ve, la librería se está consumiendo bien desde ds-repo.
 * Los switches prueban los temas claro/oscuro y alto contraste.
 */
@Composable
fun DesignSystemDemoScreen() {
    val systemDark = isSystemInDarkTheme()
    var darkTheme by rememberSaveable { mutableStateOf(systemDark) }
    var highContrast by rememberSaveable { mutableStateOf(false) }

    MyProjectTheme(darkTheme = darkTheme, highContrast = highContrast) {
        AppSurface(modifier = Modifier.fillMaxSize()) {
            var name by rememberSaveable { mutableStateOf("") }
            var showError by rememberSaveable { mutableStateOf(false) }
            val spacing = MyProjectTheme.spacing

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(spacing.large),
                verticalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                AppHeading("Design System UCB")
                AppBody("Estos componentes vienen de la librería edu.ucb.ds")
                AppLabel("Colores, tipografía y espaciados salen de MyProjectTheme")

                ThemeSwitch(label = "Modo oscuro", checked = darkTheme, onCheckedChange = { darkTheme = it })
                ThemeSwitch(label = "Alto contraste", checked = highContrast, onCheckedChange = { highContrast = it })

                AppTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        showError = false
                    },
                    label = "Nombre",
                    errorMessage = if (showError) "El nombre es obligatorio" else null
                )

                Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                    PrimaryButton(text = "Guardar", onClick = { showError = name.isBlank() })
                    SecondaryButton(text = "Limpiar", onClick = { name = ""; showError = false })
                }

                AppCard {
                    Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                        Icon(
                            imageVector = UserIcon,
                            contentDescription = "Usuario",
                            tint = MyProjectTheme.colors.primary
                        )
                        AppBody(
                            text = if (name.isBlank()) "Sin nombre" else name,
                            color = MyProjectTheme.colors.onSurface
                        )
                    }
                }

                AppList(
                    items = listOf("Tokens", "Componentes", "Publicación"),
                    modifier = Modifier.weight(1f)
                ) { item ->
                    AppListItem(
                        title = item,
                        subtitle = "Parte del Design System",
                        leadingIcon = UserIcon
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MyProjectTheme.spacing.medium)
    ) {
        Switch(checked = checked, onCheckedChange = onCheckedChange)
        AppBody(label)
    }
}
