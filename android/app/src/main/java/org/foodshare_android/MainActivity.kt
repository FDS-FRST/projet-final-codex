package org.foodshare_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                OffresScreen()
            }
        }
    }
}

@Composable
fun OffresScreen(viewModel: OffresViewModel = viewModel()) {
    val offres by viewModel.offres.observeAsState(emptyList())
    val erreur by viewModel.erreur.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.chargerOffres()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Offres disponibles (test API)", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        if (!erreur.isNullOrBlank()) {
            Text("Erreur : $erreur", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn {
            items(offres) { offre ->
                Card(modifier = Modifier.padding(4.dp)) {
                    Text(text = offre.titre, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
