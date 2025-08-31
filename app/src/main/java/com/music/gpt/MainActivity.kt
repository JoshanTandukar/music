package com.music.gpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import kotlinx.coroutines.delay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.music.gpt.ui.theme.MusicTheme
import com.music.gpt.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicTheme {
                MusicGPTApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicGPTApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var nowPlayingItem by remember { mutableStateOf<MusicItem?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // App Logo with animation
                        var logoScale by remember { mutableStateOf(1f) }
                        LaunchedEffect(Unit) {
                            while (true) {
                                animate(
                                    initialValue = 1f,
                                    targetValue = 1.1f,
                                    animationSpec = tween(2000, easing = EaseInOut)
                                ) { value, _ -> logoScale = value }
                                animate(
                                    initialValue = 1.1f,
                                    targetValue = 1f,
                                    animationSpec = tween(2000, easing = EaseInOut)
                                ) { value, _ -> logoScale = value }
                                kotlinx.coroutines.delay(1000)
                            }
                        }
                        
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .scale(logoScale)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            MusicGPTOrange,
                                            MusicGPTPink,
                                            MusicGPTPurple
                                        )
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                        Text(
                            text = "MusicGPT",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MusicGPTDark
                )
            )
        },
        bottomBar = {
            Column {
                // Now Playing Bar
                AnimatedVisibility(
                    visible = nowPlayingItem != null,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(300, easing = EaseOut)
                    ) + fadeIn(animationSpec = tween(300)),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(300, easing = EaseIn)
                    ) + fadeOut(animationSpec = tween(300))
                ) {
                    nowPlayingItem?.let { item ->
                        NowPlayingBar(
                            item = item,
                            isPlaying = isPlaying,
                            onPlayPause = { isPlaying = !isPlaying },
                            onPrevious = { /* Handle previous */ },
                            onNext = { /* Handle next */ }
                        )
                    }
                }
                
                // Bottom Navigation
                NavigationBar(
                    containerColor = MusicGPTDarkGray,
                    contentColor = Color.White
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Star, contentDescription = "Create") },
                        label = { Text("Create") },
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        label = { Text("Search") },
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Menu, contentDescription = "List") },
                        label = { Text("List") },
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 }
                    )
                }
            }
        },
        floatingActionButton = {
            var fabScale by remember { mutableStateOf(1f) }
            LaunchedEffect(Unit) {
                while (true) {
                    animate(
                        initialValue = 1f,
                        targetValue = 1.05f,
                        animationSpec = tween(1000, easing = EaseInOut)
                    ) { value, _ -> fabScale = value }
                    animate(
                        initialValue = 1.05f,
                        targetValue = 1f,
                        animationSpec = tween(1000, easing = EaseInOut)
                    ) { value, _ -> fabScale = value }
                    kotlinx.coroutines.delay(2000)
                }
            }
            
            FloatingActionButton(
                onClick = { /* Handle create action */ },
                containerColor = MusicGPTMediumGray,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                ),
                modifier = Modifier.scale(fabScale)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = MusicGPTPink
                    )
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = MusicGPTOrange
                    )
                    Text(
                        "Create",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MusicGPTDark)
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> CreateTab(
                    onItemClick = { item ->
                        nowPlayingItem = item
                        isPlaying = true
                    }
                )
                else -> Text("Coming Soon", color = Color.White)
            }
        }
    }
}

@Composable
fun NowPlayingBar(
    item: MusicItem,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MusicGPTDarkGray
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Thumbnail
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(getThumbnailBackground(item.thumbnailType))
            )
            
            // Title
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            
            // Playback Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPrevious) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Previous",
                        tint = Color.White
                    )
                }
                
                IconButton(onClick = onPlayPause) {
                    Icon(
                        if (isPlaying) Icons.Default.Star else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                IconButton(onClick = onNext) {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CreateTab(onItemClick: (MusicItem) -> Unit) {
    val musicItems = listOf(
        MusicItem(
            title = "Create a funky house song with fo",
            status = "Starting AI audio engine",
            version = "v1",
            thumbnailType = ThumbnailType.LANDSCAPE,
            isProcessing = true
        ),
        MusicItem(
            title = "Create a funky house song with",
            status = "21.4K users in queue skip",
            version = "v1",
            thumbnailType = ThumbnailType.PROGRESS,
            progress = 0
        ),
        MusicItem(
            title = "Language Training",
            status = "Create a presentation that explains how lar",
            thumbnailType = ThumbnailType.LANDSCAPE_BW,
            hasOptions = true
        ),
        MusicItem(
            title = "Bam Bam",
            status = "Generate a script for a play about the powe",
            thumbnailType = ThumbnailType.AUDIO_WAVEFORM,
            hasOptions = true
        ),
        MusicItem(
            title = "Enemy",
            status = "Compose a poem about the meanin",
            thumbnailType = ThumbnailType.MOUNTAIN_BLUE,
            hasOptions = true
        ),
        MusicItem(
            title = "Balenciaga",
            status = "Generate a poem about a los",
            thumbnailType = ThumbnailType.STARRY_NIGHT,
            hasOptions = true
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = musicItems,
            key = { it.title }
        ) { item ->
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(300, delayMillis = musicItems.indexOf(item) * 100)
                ) + fadeIn(
                    animationSpec = tween(300, delayMillis = musicItems.indexOf(item) * 100)
                )
            ) {
                MusicItemCard(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
fun MusicItemCard(
    item: MusicItem,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { 
                onClick()
                isPressed = true
                // Reset pressed state after animation
                kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                    kotlinx.coroutines.delay(100)
                    isPressed = false
                }
            }
            .scale(
                animateFloatAsState(
                    targetValue = if (isPressed) 0.95f else 1f,
                    animationSpec = tween(100),
                    label = "scale"
                ).value
            ),
        colors = CardDefaults.cardColors(
            containerColor = MusicGPTDarkGray
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Thumbnail with pulse animation for processing items
            var thumbnailScale by remember { mutableStateOf(1f) }
            LaunchedEffect(item.isProcessing) {
                if (item.isProcessing) {
                    while (true) {
                        animate(
                            initialValue = 1f,
                            targetValue = 1.1f,
                            animationSpec = tween(800, easing = EaseInOut)
                        ) { value, _ -> thumbnailScale = value }
                        animate(
                            initialValue = 1.1f,
                            targetValue = 1f,
                            animationSpec = tween(800, easing = EaseInOut)
                        ) { value, _ -> thumbnailScale = value }
                    }
                }
            }
            
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .scale(thumbnailScale)
                    .clip(RoundedCornerShape(8.dp))
                    .background(getThumbnailBackground(item.thumbnailType)),
                contentAlignment = Alignment.Center
            ) {
                when (item.thumbnailType) {
                    ThumbnailType.PROGRESS -> {
                        Text(
                            text = "${item.progress}%",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    ThumbnailType.AUDIO_WAVEFORM -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            repeat(3) { index ->
                                Box(
                                    modifier = Modifier
                                        .width(4.dp)
                                        .height((8 + index * 4).dp)
                                        .background(Color.White, RoundedCornerShape(2.dp))
                                )
                            }
                        }
                    }
                    else -> {
                        // Default landscape thumbnail
                        if (item.thumbnailType == ThumbnailType.MOUNTAIN_BLUE) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.Green, CircleShape)
                            )
                        }
                    }
                }
            }
            
            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = item.status,
                    color = if (item.isProcessing) MusicGPTPink else Color.Gray,
                    fontSize = 14.sp
                )
            }
            
            // Right side content
            when {
                item.version != null -> {
                    Text(
                        text = item.version,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(
                                MusicGPTMediumGray,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                item.hasOptions -> {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = Color.Gray,
                        modifier = Modifier.clickable { /* Handle options */ }
                    )
                }
            }
        }
        
        // Processing indicator with animation
        if (item.isProcessing) {
            var progressValue by remember { mutableStateOf(0f) }
            LaunchedEffect(Unit) {
                while (true) {
                    animate(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = tween(2000, easing = LinearEasing)
                    ) { value, _ -> progressValue = value }
                    progressValue = 0f
                }
            }
            
            LinearProgressIndicator(
                progress = { progressValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = MusicGPTPink,
                trackColor = MusicGPTMediumGray
            )
        }
    }
}

@Composable
fun getThumbnailBackground(type: ThumbnailType): Brush {
    return when (type) {
        ThumbnailType.LANDSCAPE -> Brush.verticalGradient(
            listOf(Color(0xFF87CEEB), Color(0xFF2F4F4F))
        )
        ThumbnailType.PROGRESS -> Brush.radialGradient(
            listOf(MusicGPTPurple, Color(0xFF4A148C))
        )
        ThumbnailType.LANDSCAPE_BW -> Brush.verticalGradient(
            listOf(Color.White, Color.Black)
        )
        ThumbnailType.AUDIO_WAVEFORM -> Brush.verticalGradient(
            listOf(Color(0xFF424242), Color(0xFF212121))
        )
        ThumbnailType.MOUNTAIN_BLUE -> Brush.verticalGradient(
            listOf(Color(0xFF1976D2), Color(0xFF0D47A1))
        )
        ThumbnailType.STARRY_NIGHT -> Brush.verticalGradient(
            listOf(MusicGPTPurple, Color(0xFF3F51B5))
        )
    }
}

data class MusicItem(
    val title: String,
    val status: String,
    val version: String? = null,
    val thumbnailType: ThumbnailType,
    val progress: Int = 0,
    val isProcessing: Boolean = false,
    val hasOptions: Boolean = false
)

enum class ThumbnailType {
    LANDSCAPE,
    PROGRESS,
    LANDSCAPE_BW,
    AUDIO_WAVEFORM,
    MOUNTAIN_BLUE,
    STARRY_NIGHT
}

@Preview(showBackground = true)
@Composable
fun MusicGPTAppPreview() {
    MusicTheme {
        MusicGPTApp()
    }
}