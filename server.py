#!/usr/bin/env python3
"""
ControlX Production Web Portal, Distribution Service & REST API
Built for seamless local execution and Render Python / Docker deployments.
"""

import http.server
import socketserver
import os
import json
import mimetypes
import datetime
import sys

PORT = int(os.environ.get("PORT", 10000))
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
PUBLIC_DIR = os.path.join(BASE_DIR, "public")
APK_PATH = os.path.join(PUBLIC_DIR, "downloads", "ControlX-release.apk")
START_TIME = datetime.datetime.now(datetime.timezone.utc)

mimetypes.add_type("application/vnd.android.package-archive", ".apk")
mimetypes.add_type("image/svg+xml", ".svg")
mimetypes.add_type("text/css", ".css")
mimetypes.add_type("application/javascript", ".js")

def get_apk_info():
    size_bytes = 0
    size_formatted = "18.7 MB"
    last_mod = datetime.datetime.now(datetime.timezone.utc).isoformat()
    if os.path.exists(APK_PATH):
        st = os.stat(APK_PATH)
        size_bytes = st.st_size
        size_formatted = f"{size_bytes / (1024 * 1024):.2f} MB"
        last_mod = datetime.datetime.fromtimestamp(st.st_mtime, tz=datetime.timezone.utc).isoformat()
    return {
        "appName": "ControlX",
        "packageName": "com.example.controlx",
        "version": "1.0.0",
        "versionCode": 1,
        "minSdk": 24,
        "minAndroid": "Android 7.0 (Nougat)+",
        "targetSdk": 36,
        "targetAndroid": "Android 15/16",
        "fileSize": size_formatted,
        "sizeBytes": size_bytes,
        "sha256": "981f572c2fe29cb083ae6b3d678e860d34e913c00f019c34c337a5d9b143c0a8",
        "lastModified": last_mod,
        "downloadUrl": "/download/apk"
    }

class ControlXHandler(http.server.SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=PUBLIC_DIR, **kwargs)

    def do_HEAD(self):
        if self.path in ("/api/health", "/api/app-info", "/api/features"):
            self.send_response(200)
            self.send_header("Content-Type", "application/json")
            self.send_header("Access-Control-Allow-Origin", "*")
            self.end_headers()
        elif self.path in ("/download/apk", "/downloads/ControlX-release.apk"):
            if os.path.exists(APK_PATH):
                self.send_response(200)
                self.send_header("Content-Type", "application/vnd.android.package-archive")
                self.send_header("Content-Disposition", 'attachment; filename="ControlX-release.apk"')
                self.send_header("Content-Length", str(os.path.getsize(APK_PATH)))
                self.end_headers()
            else:
                self.send_response(404)
                self.end_headers()
        else:
            super().do_HEAD()

    def do_GET(self):
        # 1. Health check endpoint for Render
        if self.path == "/api/health":
            self.send_response(200)
            self.send_header("Content-Type", "application/json")
            self.send_header("Access-Control-Allow-Origin", "*")
            self.end_headers()
            now = datetime.datetime.now(datetime.timezone.utc)
            uptime_seconds = (now - START_TIME).total_seconds()
            payload = {
                "status": "ok",
                "uptime": uptime_seconds,
                "timestamp": now.isoformat(),
                "service": "ControlX Production Web Service",
                "environment": os.environ.get("NODE_ENV", "production")
            }
            self.wfile.write(json.dumps(payload, indent=2).encode("utf-8"))
            return

        # 2. App Metadata
        if self.path == "/api/app-info":
            self.send_response(200)
            self.send_header("Content-Type", "application/json")
            self.send_header("Access-Control-Allow-Origin", "*")
            self.end_headers()
            self.wfile.write(json.dumps(get_apk_info(), indent=2).encode("utf-8"))
            return

        # 3. Features API
        if self.path == "/api/features":
            self.send_response(200)
            self.send_header("Content-Type", "application/json")
            self.send_header("Access-Control-Allow-Origin", "*")
            self.end_headers()
            features = {
                "totalModules": 32,
                "categories": [
                    {
                        "name": "Device Hardware Control",
                        "items": [
                            {"name": "Torch / Flashlight", "desc": "Camera torch controller with visual toggle"},
                            {"name": "Bluetooth Hub", "desc": "Adapter state management & discovery"},
                            {"name": "Wi-Fi Manager", "desc": "Wi-Fi toggle & system integration"},
                            {"name": "Vibrator Engine", "desc": "Custom haptic feedback vibration pulses"},
                            {"name": "Direct Phone Dialer", "desc": "One-touch direct calling with permission handling"}
                        ]
                    },
                    {
                        "name": "Integrated Sensor Suite",
                        "items": [
                            {"name": "Light Sensor", "desc": "Lux measurement and dynamic brightness responses"},
                            {"name": "Accelerometer", "desc": "3-Axis motion tracking and gesture triggers"},
                            {"name": "Gravity Sensor", "desc": "Gravitational vector analysis"},
                            {"name": "Sensor Music Player", "desc": "Ambient sensor-driven audio control"}
                        ]
                    },
                    {
                        "name": "Media & Interactive Tools",
                        "items": [
                            {"name": "Music Player", "desc": "Audio playback with UI controls"},
                            {"name": "Video Player", "desc": "Hardware-accelerated media viewer"},
                            {"name": "In-App Browser", "desc": "Safe in-app web explorer"},
                            {"name": "Camera & Photo Capture", "desc": "Integrated photo capture"},
                            {"name": "Scientific Calculator", "desc": "Arithmetic calculations"}
                        ]
                    },
                    {
                        "name": "Interactive Quiz Arena",
                        "items": [
                            {"name": "4-Stage Quiz Engine", "desc": "Multi-tier interactive quizzes with score tracking"}
                        ]
                    },
                    {
                        "name": "Dual Authentication & Cloud Sync",
                        "items": [
                            {"name": "Firebase Cloud Auth", "desc": "Online secure account registration & login"},
                            {"name": "Firebase Realtime DB", "desc": "Real-time user profile cloud sync"},
                            {"name": "Offline SQLite Storage", "desc": "Local credential and data store"}
                        ]
                    }
                ]
            }
            self.wfile.write(json.dumps(features, indent=2).encode("utf-8"))
            return

        # 4. APK Download Stream
        if self.path == "/download/apk" or self.path == "/downloads/ControlX-release.apk":
            if os.path.exists(APK_PATH):
                self.send_response(200)
                self.send_header("Content-Type", "application/vnd.android.package-archive")
                self.send_header("Content-Disposition", 'attachment; filename="ControlX-release.apk"')
                self.send_header("Content-Length", str(os.path.getsize(APK_PATH)))
                self.send_header("Cache-Control", "public, max-age=86400")
                self.end_headers()
                with open(APK_PATH, "rb") as f:
                    while chunk := f.read(65536):
                        self.wfile.write(chunk)
                return
            else:
                self.send_response(404)
                self.send_header("Content-Type", "application/json")
                self.end_headers()
                self.wfile.write(b'{"error": "Release APK not found"}')
                return

        # Fallback to standard static file serving or index.html
        requested_file = os.path.join(PUBLIC_DIR, self.path.lstrip("/"))
        if not os.path.exists(requested_file) or os.path.isdir(requested_file):
            index_file = os.path.join(PUBLIC_DIR, "index.html")
            if os.path.exists(index_file):
                self.send_response(200)
                self.send_header("Content-Type", "text/html; charset=utf-8")
                self.end_headers()
                with open(index_file, "rb") as f:
                    self.wfile.write(f.read())
                return

        return super().do_GET()

def run():
    socketserver.TCPServer.allow_reuse_address = True
    with socketserver.TCPServer(("0.0.0.0", PORT), ControlXHandler) as httpd:
        print("=============================================")
        print(f" ControlX Production Service is Running")
        print(f" Port:        {PORT}")
        print(f" URL:         http://localhost:{PORT}")
        print(f" Health:      http://localhost:{PORT}/api/health")
        print(f" App Info:    http://localhost:{PORT}/api/app-info")
        print(f" Download:    http://localhost:{PORT}/download/apk")
        print("=============================================")
        try:
            httpd.serve_forever()
        except KeyboardInterrupt:
            print("\nShutting down server.")

if __name__ == "__main__":
    run()
