# Aplicatie Gestiune Parinte-Copil - Spotify

## Descriere
O aplicatie desktop CRUD care gestioneaza o relatie 1:N (un parinte are mai multi copii) intre albume si melodii

## Tehnologii Utilizate
- **Limbaj:** Java
- **Interfata Grafica:** JavaFX
- **Baza de Date:** PostgreSQL


## Configurare Baza de Date
1. Deschideti instrumentul de gestiune SQL (pgAdmin)
2. Rulati scriptul 'domain.sql'
3. Scriptul va crea tabelele

## Configurare Conexiune
- Localizati fisierul 'bd.config' (src/groovy/resources/spotify/spotify/bd.config)
- Modificati url-ul cu cel al bazei voastre de date
- Modificati user-ul cu user-ul vostru
- Modificati parola cu parola voastra