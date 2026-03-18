import json

from auxiliar import dibujar_mapa_coloreado, generar_mapa_grafo

COLORES = ["red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime"]

def realizar_voraz(grafo):
    resultado = {}

    # Recorremos todos los nodos del grafo
    for nodo_actual in grafo.keys():
        vecinos = grafo[nodo_actual]
        
        # Creamos una copia de los colores disponibles para este nodo
        disponibles = list(COLORES)
        
        # Quitamos los colores ya usados por los vecinos
        for vecino in vecinos:
            # Si el vecino ya tiene un color 
            if vecino in resultado:
                color_vecino = resultado[vecino]
                # lo quitamos
                if color_vecino in disponibles:
                    disponibles.remove(color_vecino)
        
        # Asignamos el primer color disponible
        if disponibles:
            resultado[nodo_actual] = disponibles[0]

    return resultado

if __name__ == "__main__":
    n = 4
    mapa = generar_mapa_grafo(n)
    solucion = realizar_voraz(mapa["grafo"])

    if solucion:
        print("Solución encontrada:", solucion)
        dibujar_mapa_coloreado(mapa, solucion)
        with open('sols/solucion.json', 'w') as f:
            json.dump(solucion, f)
            f.close()
    else:
        print("No se encontró solución.")
