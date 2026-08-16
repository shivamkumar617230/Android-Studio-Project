document.addEventListener('DOMContentLoaded', () => {
  // 1. Interactive Feature Tabs Switcher
  const tabButtons = document.querySelectorAll('.tab-btn');
  const tabContents = document.querySelectorAll('.tab-content');

  tabButtons.forEach(button => {
    button.addEventListener('click', () => {
      const targetTab = button.getAttribute('data-tab');

      tabButtons.forEach(btn => btn.classList.remove('active'));
      tabContents.forEach(content => content.classList.remove('active'));

      button.classList.add('active');
      const targetElement = document.getElementById(`tab-${targetTab}`);
      if (targetElement) {
        targetElement.classList.add('active');
      }
    });
  });

  // 2. Mockup Real-time Clock
  const updateMockTime = () => {
    const timeEl = document.getElementById('mock-time');
    if (timeEl) {
      const now = new Date();
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');
      timeEl.textContent = `${hours}:${minutes}`;
    }
  };
  updateMockTime();
  setInterval(updateMockTime, 10000);

  // 3. Mockup Simulated Sensor Lux Animation
  const mockLux = document.getElementById('mock-lux');
  if (mockLux) {
    setInterval(() => {
      const randomLux = Math.floor(Math.random() * 350) + 250;
      mockLux.textContent = `${randomLux} Lux`;
    }, 3000);
  }

  // 4. Fetch Live Release Metadata from API
  fetch('/api/app-info')
    .then(res => res.json())
    .then(data => {
      if (data) {
        const heroMeta = document.getElementById('hero-apk-meta');
        if (heroMeta && data.version && data.fileSize) {
          heroMeta.textContent = `v${data.version} • ${data.fileSize} • Signed Production`;
        }

        const tableVersion = document.getElementById('table-version');
        if (tableVersion && data.version) {
          tableVersion.textContent = `${data.version} (Build ${data.versionCode || 1})`;
        }

        const tableSize = document.getElementById('table-size');
        if (tableSize && data.fileSize) {
          tableSize.textContent = data.fileSize;
        }

        const tableSha = document.getElementById('table-sha');
        if (tableSha && data.sha256) {
          tableSha.textContent = data.sha256;
        }
      }
    })
    .catch(err => {
      console.log('Using fallback static metadata for offline preview.');
    });

  // 5. Check Live API Health
  fetch('/api/health')
    .then(res => res.json())
    .then(data => {
      const statusText = document.getElementById('status-text');
      if (statusText && data.status === 'ok') {
        statusText.textContent = 'Production Live';
      }
    })
    .catch(() => {
      const statusText = document.getElementById('status-text');
      if (statusText) {
        statusText.textContent = 'Preview Mode';
      }
    });

  // 6. Navbar Scroll Background Blur Effect
  const navbar = document.getElementById('navbar');
  window.addEventListener('scroll', () => {
    if (window.scrollY > 40) {
      navbar.style.background = 'rgba(7, 9, 19, 0.92)';
      navbar.style.boxShadow = '0 10px 30px rgba(0, 0, 0, 0.5)';
    } else {
      navbar.style.background = 'rgba(7, 9, 19, 0.75)';
      navbar.style.boxShadow = 'none';
    }
  });
});
