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
deadline return book /by 31-12-2026 23:59
bye
```

Expected output:

```text
Got it. I've added this task:
[D][ ] return book (by: Dec 31 2026 11:59PM)
Now you have 1 task in the list.
____________________________________________________________
Bye. Hope to see you again soon!
```

## Test case: add an event task

Aim: Verify that an event command stores and displays its start and end times.

Input:

```text
event project meeting /from 31-12-2026 14:00 /to 31-12-2026 16:00
bye
```

Expected output:

```text
Got it. I've added this task:
[E][ ] project meeting (from: Dec 31 2026 02:00PM to: Dec 31 2026 04:00PM)
Now you have 1 task in the list.
____________________________________________________________
Bye. Hope to see you again soon!
```
