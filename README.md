# Task 2 of software-architecture-tasks

You can run 3 different Hazelcast for 3 different tasks.

1. To work with a Distributed Map, launch members with "_dist_map" in the end of their class name.
2. To work with different locks of a Distributed Map, run members with the class names ending in "_dist_map_locks" and set a command-line argument (one of "none", "optimistic" and "pessimistic").
3. To work with a Distributed Queue, run 4 members with class names ending in "_dist_queue".

Don't forget to run Management Center via console or docker command.