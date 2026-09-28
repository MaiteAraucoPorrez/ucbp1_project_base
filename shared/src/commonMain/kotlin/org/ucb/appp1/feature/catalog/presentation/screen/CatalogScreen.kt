package org.ucb.appp1.feature.catalog.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.feature.catalog.domain.model.MovieModel
import org.ucb.appp1.feature.catalog.presentation.viewmodel.CatalogEffects
import org.ucb.appp1.feature.catalog.presentation.viewmodel.CatalogEvents
import org.ucb.appp1.feature.catalog.presentation.viewmodel.CatalogViewModel

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is CatalogEffects.NavigateToMovieDetail -> {
                    println("Navigate to detail of ${effect.movieId}")
                }
                is CatalogEffects.ShowToast -> {
                    println("TOAST: ${effect.message}")
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize().statusBarsPadding()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.movies) { movie ->
                    MovieItem(
                        movie = movie,
                        onClick = { viewModel.emitEvent(CatalogEvents.OnMovieClicked(movie)) }
                    )
                }
            }
        }

        state.errorMessage?.let { error ->
            Text(
                text = error,
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieModel,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.66f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}
