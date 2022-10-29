package component;

import org.springframework.stereotype.Component;

@Component
class Notas {
    String tag;
   String content;
   Boolean active; // 1 si está activo, 0 si está archivado
}