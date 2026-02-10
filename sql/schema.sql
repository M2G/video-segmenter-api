CREATE TABLE videos (
                        id TEXT PRIMARY KEY,
                        file_path TEXT NOT NULL,
                        status TEXT NOT NULL
);


ALTER TABLE videos
    ADD COLUMN duration DOUBLE PRECISION,
ADD COLUMN segments_count INTEGER;
