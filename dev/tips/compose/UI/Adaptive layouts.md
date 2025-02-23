https://developer.android.com/jetpack/compose/layouts/adaptive

# Adaptive Layouts

- Window Size Classes. Created To help handle different screens.
- Refer: [JetNews](https://github.com/android/compose-samples/tree/master/JetNews)
- Android studio: "Reference devices". Emulator changes dynamically.
- Use Box With Constraints. costly

- Actual portion of screen should always be based on window metrics from jetpack WindowManager.
- individual, reusable composables should avoid implicitly depending on “global” size information. Not reusable.