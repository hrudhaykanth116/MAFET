# Media Module

A Kotlin Multiplatform (KMP) feature module for browsing and downloading photos and videos from Pexels API.

## Features

### ✅ Implemented (Phase 1 & 2)
- Browse curated photos and videos in Pinterest-style staggered grid
- Search photos/videos with real-time results
- View full detail of media items with photographer attribution
- Download media to device (photos and videos)
- Share media with photographer attribution
- Open photographer profile on Pexels
- Advanced filtering (orientation, color, size)
- Infinite scroll pagination
- Recent and trending search suggestions

## Platform Support

| Feature | Android | iOS | Desktop |
|---------|---------|-----|---------|
| Browse & Search | ✅ | ✅ | ✅ |
| View Details | ✅ | ✅ | ✅ |
| Download | ✅ | ❌ TODO | ❌ TODO |
| Share | ✅ | ❌ TODO | ❌ TODO |
| Open URLs | ✅ | ❌ TODO | ❌ TODO |

## Android Permissions

### Required Permissions

The following permission is declared in `androidApp/src/main/AndroidManifest.xml`:

```xml
<!-- For downloading media files (Android 9 and below only) -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="28" />
```

### Permission Behavior by Android Version

**Android 10+ (API 29+):**
- ✅ No permissions required for downloading
- Uses MediaStore API with Scoped Storage
- Files saved to Downloads folder automatically visible in gallery
- Images go to `MediaStore.Images.Media`
- Videos go to `MediaStore.Video.Media`

**Android 9 and below (API < 29):**
- ⚠️ Requires `WRITE_EXTERNAL_STORAGE` runtime permission
- Uses legacy file system API
- Downloads to `Environment.DIRECTORY_DOWNLOADS`
- Triggers media scanner to make files visible

### Notes
- Permission is automatically granted on Android 10+ due to Scoped Storage
- For Android 9 and below, if permission is denied, download will fail silently
- No UI prompts for permissions implemented yet (would need Activity context)

## API Configuration

Add your Pexels API key to `secrets.properties`:
```properties
PEXELS_API_KEY=your_pexels_api_key_here
```

Get your API key from: https://www.pexels.com/api/

## Architecture

### Domain Layer
- **Models**: MediaItem, MediaType, OrientationType, ColorFilter, SizeFilter, FilterState
- **Use Cases**: GetCuratedMediaUseCase, SearchMediaUseCase, GetMediaDetailUseCase
- **Mappers**: MediaItemMapper (PhotoResponse/VideoResponse → MediaItem)

### Data Layer
- **API**: PexelsApiServiceKtor (Ktor client)
- **Repository**: PexelsRepository (DomainResult wrapper)
- **Remote**: PexelsRemoteDataSource

### Presentation Layer
- **Home Screen**: Browse curated media with filters
- **Search Screen**: Search with suggestions and filters
- **Detail Screen**: Full media view with actions

### Platform Actions (expect/actual)
- **MediaPlatformActions**: Platform-specific operations
  - `openUrl(url)` - Open browser
  - `shareContent(url, text)` - Share dialog
  - `downloadFile(url, filename)` - Download to device

## Pexels API Rate Limits

- **Free Tier**: 200 requests/hour
- **Attribution Required**: Photographer name + link to Pexels

## Future Enhancements (Phase 3)

- [ ] Favorites with local Room database
- [ ] Collections/albums
- [ ] Offline mode
- [ ] Wallpaper setter (Android)
- [ ] Video playback in detail view
- [ ] Grid/List view toggle
- [ ] iOS download/share implementation
- [ ] Desktop download/share implementation
