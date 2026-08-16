const express = require('express');
const path = require('path');
const fs = require('fs');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 10000;

// Enable CORS
app.use(cors());

// Parse JSON bodies
app.use(express.json());

// Path to release APK
const APK_PATH = path.join(__dirname, 'public', 'downloads', 'ControlX-release.apk');

// Helper to get APK metadata
function getApkMetadata() {
  let sizeBytes = 0;
  let sizeFormatted = '18.7 MB';
  let lastModified = new Date().toISOString();

  if (fs.existsSync(APK_PATH)) {
    const stats = fs.statSync(APK_PATH);
    sizeBytes = stats.size;
    sizeFormatted = (sizeBytes / (1024 * 1024)).toFixed(2) + ' MB';
    lastModified = stats.mtime.toISOString();
  }

  return {
    appName: 'ControlX',
    packageName: 'com.example.controlx',
    version: '1.0.0',
    versionCode: 1,
    minSdk: 24,
    minAndroid: 'Android 7.0 (Nougat)+',
    targetSdk: 36,
    targetAndroid: 'Android 15/16',
    fileSize: sizeFormatted,
    sizeBytes: sizeBytes,
    sha256: '981f572c2fe29cb083ae6b3d678e860d34e913c00f019c34c337a5d9b143c0a8',
    lastModified: lastModified,
    downloadUrl: '/download/apk'
  };
}

// Serve static assets from public with proper caching
app.use(express.static(path.join(__dirname, 'public'), {
  maxAge: '1h',
  setHeaders: (res, filePath) => {
    if (filePath.endsWith('.apk')) {
      res.setHeader('Cache-Control', 'public, max-age=86400');
    }
  }
}));

// 1. Health Check Endpoint (Render Monitoring)
app.get('/api/health', (req, res) => {
  res.status(200).json({
    status: 'ok',
    uptime: process.uptime(),
    timestamp: new Date().toISOString(),
    service: 'ControlX Production Web Service',
    environment: process.env.NODE_ENV || 'production'
  });
});

// 2. Application Info & Metadata Endpoint
app.get('/api/app-info', (req, res) => {
  res.status(200).json(getApkMetadata());
});

// 3. Feature Matrix API
app.get('/api/features', (req, res) => {
  res.status(200).json({
    totalModules: 32,
    categories: [
      {
        name: 'Device Hardware Control',
        icon: 'hardware',
        items: [
          { name: 'Torch / Flashlight', desc: 'Hardware camera torch control with on/off visual indicator' },
          { name: 'Bluetooth Hub', desc: 'Bluetooth adapter state toggle, discovery & Android 12+ connect permissions' },
          { name: 'Wi-Fi Manager', desc: 'Quick Wi-Fi system toggle & network settings integration' },
          { name: 'Vibrator Engine', desc: 'Hardware haptic feedback motor control & custom pulse patterns' },
          { name: 'Direct Phone Dialer', desc: 'Phone call launcher with runtime permission validation' }
        ]
      },
      {
        name: 'Integrated Sensor Suite',
        icon: 'sensors',
        items: [
          { name: 'Light Sensor', desc: 'Ambient lux measurement with interactive real-time screen brightness reactions' },
          { name: 'Accelerometer', desc: '3-Axis X/Y/Z motion tracking and shake gesture recognition' },
          { name: 'Gravity Sensor', desc: 'Real-time gravitational force vector orientation analysis' },
          { name: 'Sensor Music Player', desc: 'Interactive audio controller dynamically triggered by ambient sensor inputs' }
        ]
      },
      {
        name: 'Media & Interactive Tools',
        icon: 'media',
        items: [
          { name: 'Integrated Music Player', desc: 'Embedded audio player supporting background tracks and playback controls' },
          { name: 'Video Player', desc: 'Hardware accelerated video player with playback controls' },
          { name: 'In-App Web Browser', desc: 'Full-featured webview browser with navigation controls' },
          { name: 'Camera & Photo Capture', desc: 'Integrated camera capture and photo viewer' },
          { name: 'Scientific Calculator', desc: 'High-precision arithmetic and calculation tool' }
        ]
      },
      {
        name: 'Interactive Quiz Arena',
        icon: 'quiz',
        items: [
          { name: '4-Stage Quiz Engine', desc: 'Multi-level interactive challenge with score tracking across 4 distinct quiz stages' }
        ]
      },
      {
        name: 'Dual Authentication & Cloud Sync',
        icon: 'auth',
        items: [
          { name: 'Firebase Cloud Auth', desc: 'Secure cloud authentication with Firebase Auth' },
          { name: 'Firebase Realtime Database', desc: 'Real-time user profile sync & cloud storage' },
          { name: 'Offline SQLite Storage', desc: 'Local SQLite offline database authentication when disconnected' }
        ]
      }
    ]
  });
});

// 4. Direct APK Download Streaming Endpoint
app.get('/download/apk', (req, res) => {
  if (!fs.existsSync(APK_PATH)) {
    return res.status(404).json({
      error: 'Release APK not found. Please build the release binary first.'
    });
  }

  const filename = 'ControlX-release.apk';
  res.setHeader('Content-Disposition', `attachment; filename="${filename}"`);
  res.setHeader('Content-Type', 'application/vnd.android.package-archive');

  const fileStream = fs.createReadStream(APK_PATH);
  fileStream.pipe(res);
});

// Fallback to index.html for SPA / client-side routing
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// Start Server
const server = app.listen(PORT, '0.0.0.0', () => {
  console.log(`=============================================`);
  console.log(` ControlX Production Service is Running`);
  console.log(` Environment: ${process.env.NODE_ENV || 'production'}`);
  console.log(` Port:        ${PORT}`);
  console.log(` URL:         http://localhost:${PORT}`);
  console.log(` Health:      http://localhost:${PORT}/api/health`);
  console.log(` App Info:    http://localhost:${PORT}/api/app-info`);
  console.log(` Download:    http://localhost:${PORT}/download/apk`);
  console.log(`=============================================`);
});

// Graceful Shutdown
process.on('SIGTERM', () => {
  console.log('SIGTERM received. Shutting down gracefully...');
  server.close(() => {
    console.log('Process terminated.');
  });
});
