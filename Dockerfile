# Multi-stage Dockerfile for ControlX Production Web Portal & Distribution API
FROM node:20-alpine AS runner

WORKDIR /app

# Set production environment
ENV NODE_ENV=production
ENV PORT=10000

# Install dependencies
COPY package*.json ./
RUN npm ci --only=production

# Copy application files and distribution assets
COPY server.js ./
COPY public ./public

# Expose service port
EXPOSE 10000

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:10000/api/health || exit 1

# Start production server
CMD ["node", "server.js"]
