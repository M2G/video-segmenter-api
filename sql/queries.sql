-- name: InsertVideo :exec
INSERT INTO videos (id, file_path, status)
VALUES ($1, $2, $3);

-- name: UpdateVideoStatus :exec
UPDATE videos
SET status = $2
WHERE id = $1;

-- name: GetVideoById :one
SELECT id, file_path, status
FROM videos
WHERE id = $1;

-- name: UpdateVideoMetadata :exec
UPDATE videos
SET
    duration = $2,
    segments_count = $3,
    status = $4
WHERE id = $1;

-- name: FindVideoById :one
SELECT id, file_path, status, duration, segments_count
FROM videos
WHERE id = $1;