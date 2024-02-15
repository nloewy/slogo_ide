# Collections API Lab Discussion
### Abhishek Chataut, Yash Gangavarapu, Noah Loewy, Bodhaansh Ravipati
### Team 5


## In your experience using these collections, are they hard or easy to use?

In our experience, they have been easy to use. All the methods for different collections are the same because of inheritance, which makes it easy, and the methods are all named well and very self explanatory.

## In your experience using these collections, do you feel mistakes are easy to avoid?

In our experience, if you read the API, most of these mistakes are easy to avoid. For example, I used to have a lot of mistakes when I tried to assign a value to the result of Collections.sort(), when it in reality does return null.

## What methods are common to all collections (except Maps)?

Add, clear, contains, remove, size, isEmpty, retainAll are examples of some methods that are common to all collections.

## What methods are common to all Deques?

addFirst, addLast, getFirst, getLast, removeFirst, removeLast are examples of methods that are common to all deques.

## What is the purpose of each interface implemented by LinkedList?

LinkedList implements 7 interfaces. Serializable allows for linked lists to be serialized and deserialized. Cloneable lets them be mutated. Iterable lets them be iterated over with an enhanced for loop, for example. Collections allows actions like getting and setting. Deque allows for constant time operations from the front and the back. Lists allows to be indexed. Queue provides additional insertion and extraction operations.

## How many different implementations are there for a Set?
9 direct implementations: AbstractSet, ConcurrentHashMap.KeySetView, ConcurrentSkipListSet, CopyOnWriteArraySet, EnumSet, HashSet, JobStateReasons, LinkedHashSet, TreeSet, and 3 sub interfaces EventSet, NavigableSet<E>, SortedSet<E>


## What is the purpose of each superclass of PriorityQueue?

Abstract Queue provides skeletal implementations of queue operations, that the priority queue class overrides and gives its own implemtations for, such as add, remove, and element (based on push, poll, peek).

## What is the purpose of the Collections utility class?

A Collections utility provides an API for working with classes under the Collections frameworks, incorporating methods such as Collections.max, Collections.min, Collections.sort.

## API Characterics applied to Collections API

* Easy to learn

* Encourages extension

* Leads to readable code

* Hard to misuse

All of these characteristics would apply to the Collections API, as it is very well designed, and clearly easily extendable, as explained above. The names of the methods make it easy to use and understand for users. 
