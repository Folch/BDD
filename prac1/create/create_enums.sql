-- Enum que indica H si és un home i D si és dona.
-- Ens asegurem que el valor entrat sigui 'H' o 'D'.
CREATE TYPE gender AS ENUM ('H', 'D');

-- Enum que indica B si és una blanca i N si és una negra.
-- Ens asegurem que el valor entrat sigui 'B' o 'N'.
CREATE TYPE color AS ENUM ('B', 'N');

-- Enum per indicar el tipus de pesa alhora de fer un moviment.
CREATE TYPE pesa AS ENUM('peó','alfil','caball','torre','rei', 'reina');

