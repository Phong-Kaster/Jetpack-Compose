package com.example.jetpack.ui.fragment.sharedelementtransition.component


/*@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainContent(
    onShowDetails: () -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Row(
        modifier = modifier.clickable { onShowDetails() }
    ) {
        with(sharedTransitionScope) {
            Image(
                painter = painterResource(id = R.drawable.ic_bundeswehr),
                contentScale = ContentScale.Crop,
                contentDescription = "Bundeswehr",
                modifier = Modifier
                    .size(100.dp)
                    .sharedElement(
                        rememberSharedContentState(key = "image"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),

            )
        }
    }
}*/

