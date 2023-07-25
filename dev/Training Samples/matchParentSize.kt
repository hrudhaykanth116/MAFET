the child Spacer takes its size from its parent Box,
which in turn takes its size from the biggest children,
ArtistCard in this case.

Box {
    Spacer(
        Modifier
            .matchParentSize()
            .background(Color.LightGray)
    )
    ArtistCard()
}

If fillMaxSize were used instead of matchParentSize,
the Spacer would take all the available space allowed to the parent,
in turn causing the parent to expand and fill all the available space.