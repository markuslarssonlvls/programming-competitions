package algorithms

import java.util.*

interface Graph {
    val numberOfNodes: Int
    var numberOfEdges: Int

    fun adjacentNodes(from: Int): Collection<Int>

    fun nodes(): IntRange = 0 until numberOfNodes
}

// BFS - startar i node 1. Går igenom alla grannar innan den går ett steg djupare. LAGER PER LAGER

fun bfs_iterative(
        graph: Graph,
        levelOrder: ((Int) -> Unit)? = null
) {
    val visited = BooleanArray(graph.numberOfNodes)
    val queue = ArrayDeque<Int>()
    for (i in 0 until graph.numberOfNodes) {
        if (!visited[i]) {
            queue.add(i)
            visited[i] = true
            levelOrder?.invoke(i)
            while (!queue.isEmpty()) {
                val node = queue.poll()
                for (w in graph.adjacentNodes(node)) {
                    if (!visited[w]) {
                        queue.add(w)
                        visited[w] = true
                        levelOrder?.invoke(i)
                    }
                }
            }
        }
    }
}

// DFS - startar i node 1. Går så långt ner det går hela tiden. Kan välja att processa före eller efter (om efter så kommer huvudnoden blir sist)

fun dfs_iterative(
        graph: Graph,
        preorder: ((Int) -> Unit)? = null,
        postorder: ((Int) -> Unit)? = null
) {
    val visited = IntArray(graph.numberOfNodes)
    val queue = Stack<Int>()
    for (i in 0 until graph.numberOfNodes) {
        if (visited[i] == 0) {
            queue.push(i)
            visited[i] = 1
            while (!queue.isEmpty()) {
                val node = queue.pop()
                if (visited[node] == 1) {
                    visited[i] = 2
                    preorder?.invoke(i)
                    queue.push(node)
                    for (w in graph.adjacentNodes(node)) {
                        if (visited[w] == 0) {
                            queue.push(w)
                            visited[w] = 1
                        }
                    }
                } else {
                    visited[i] = 3
                    postorder?.invoke(i)
                }
            }
        }
    }
}

fun dfs_recursiverecursive(graph: Graph,
                           preorder: ((Int) -> Unit)? = null,
                           postorder: ((Int) -> Unit)? = null) {
    val visited = BooleanArray(graph.numberOfNodes)
    for (i in 0 until graph.numberOfNodes) {
        if (!visited[i]) {
            dfs_recursive_algorithm(i, graph, visited, preorder, postorder)
        }
    }
}

private fun dfs_recursive_algorithm(
        node: Int,
        graph: Graph,
        visited: BooleanArray,
        preorder: ((Int) -> Unit)? = null,
        postorder: ((Int) -> Unit)? = null
) {
    visited[node] = true
    preorder?.invoke(node)
    for (w in graph.adjacentNodes(node)) {
        if (!visited[w]) {
            dfs_recursive_algorithm(w, graph, visited, preorder, postorder)
        }
    }
    postorder?.invoke(node)
}

