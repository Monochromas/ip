# Botavius UI test plan

Each test starts a fresh application session.

## Test case: add and list a task

Aim: Verify that a normal command is added as a task and that `list` displays it.

Input:

```text
borrow book
list
bye
```

Expected output:

```text
Got it. I've added this task:
[T][ ] borrow book
Now you have 1 task in the list.
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
____________________________________________________________
Bye. Hope to see you again soon!
```

## Test case: add a deadline task

Aim: Verify that a deadline command stores and displays its deadline.

Input:

```text
deadline return book /by Sunday
bye
```

Expected output:

```text
Got it. I've added this task:
[D][ ] return book (by: Sunday)
Now you have 1 task in the list.
____________________________________________________________
Bye. Hope to see you again soon!
```

## Test case: add an event task

Aim: Verify that an event command stores and displays its start and end times.

Input:

```text
event project meeting /from Mon 2pm /to 4pm
bye
```

Expected output:

```text
Got it. I've added this task:
[E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 1 task in the list.
____________________________________________________________
Bye. Hope to see you again soon!
```
