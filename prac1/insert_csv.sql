\echo Insertant les taules fixes (hotels, sales, paisos, jornades)
COPY hotel FROM './csv/hotels.csv' DELIMITER ',' CSV HEADER 
