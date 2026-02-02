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
