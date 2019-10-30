package algorithms

import java.util.*

interface Graph {
    val numberOfNodes: Int
    var numberOfEdges: Int

    fun adjacentNodes(fromNode: Int): Collection<Int>

    fun nodes(): IntRange = 0 until numberOfNodes
}

// undirected
class UndirectedUnweightedGraph(public override val numberOfNodes: Int) : Graph {
    override var numberOfEdges: Int = 0
    private val edges: Array<Stack<Int>> = Array(numberOfNodes) { Stack<Int>() }

    public fun addEdge(n1: Int, n2: Int) {
        edges[n1].add(n2)
        edges[n2].add(n1)
        numberOfEdges++
    }

    override fun adjacentNodes(fromNode: Int): Collection<Int> {
        return edges[fromNode]
    }

    fun degree(node: Int): Int {
        return edges[node].size
    }
}

class UndirectedWeightedGraph(override val numberOfNodes: Int) : Graph {
    override var numberOfEdges: Int = 0
    private val edges: Array<Stack<Edge>> = Array(numberOfNodes) { Stack<Edge>() }

    class Edge(val n1: Int, val n2: Int, val weight: Double) : Comparable<Edge> {
        override fun compareTo(other: Edge): Int {
            return this.weight.compareTo(other.weight)
        }

        fun other(s: Int): Int {
            if (s == n1) return n2
            if (s == n2) return n1
            throw IllegalArgumentException()
        }
    }

    fun addEdge(n1: Int, n2: Int, weight: Double) {
        val edge = Edge(n1, n2, weight)
        edges[n1].add(edge)
        edges[n2].add(edge)
        numberOfEdges++
    }

    fun adjacentEdges(node: Int): Collection<Edge> {
        return edges[node]
    }

    override fun adjacentNodes(fromNode: Int): Collection<Int> {
        return adjacentEdges(fromNode).map { it.other(fromNode) }
    }

    fun degree(node: Int): Int {
        return edges[node].size
    }

    fun edges(): Collection<Edge> {
        return edges.flatMap { it }
    }
}

// directed

class DirectedUnweightedGraph(override val numberOfNodes: Int) : Graph {
    override var numberOfEdges: Int = 0
    private val edges: Array<Stack<Int>> = Array(numberOfNodes) { Stack<Int>() }
    private val indegree: IntArray = IntArray(numberOfNodes)

    fun addEdge(from: Int, to: Int) {
        edges[from].add(to)
        indegree[to]++
        numberOfEdges++
    }

    override fun adjacentNodes(fromNode: Int): Collection<Int> {
        return edges[fromNode]
    }

    fun outdegree(from: Int): Int {
        return edges[from].size
    }

    fun indegree(node: Int): Int {
        return indegree[node]
    }
}

class DirectedWeightedGraph(public override val numberOfNodes: Int) : Graph {
    override var numberOfEdges: Int = 0
    private val edges: Array<Stack<Edge>> = Array(numberOfNodes) { Stack<Edge>() }
    private val indegree: IntArray = IntArray(numberOfNodes)

    class Edge(val from: Int, val to: Int, val weight: Double)

    fun addEdge(from: Int, to: Int, weight: Double) {
        val edge = Edge(from, to, weight)
        edges[from].add(edge)
        indegree[to]++
        numberOfEdges++
    }

    fun edges(): Collection<Edge> {
        val stack = Stack<Edge>()
        edges.flatMap { it }.forEach { stack.push(it) }
        return stack
    }

    fun adjacentEdges(from: Int): Collection<Edge> {
        return edges[from]
    }

    override fun adjacentNodes(fromNode: Int): Collection<Int> {
        return adjacentEdges(fromNode).map { it.to }
    }

    fun outdegree(v: Int): Int {
        return edges[v].size
    }
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
                for (n2 in graph.adjacentNodes(node)) {
                    if (!visited[n2]) {
                        queue.add(n2)
                        visited[n2] = true
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
                    for (n2 in graph.adjacentNodes(node)) {
                        if (visited[n2] == 0) {
                            queue.push(n2)
                            visited[n2] = 1
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
    for (n2 in graph.adjacentNodes(node)) {
        if (!visited[n2]) {
            dfs_recursive_algorithm(n2, graph, visited, preorder, postorder)
        }
    }
    postorder?.invoke(node)
}

