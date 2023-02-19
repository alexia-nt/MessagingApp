# Εργασία Δικτυακού Προγραμματισμού

Αλεξία Νταντουρή (ΑΕΜ: 3871)

Ένα κατανεμημένο σύστημα ανταλλαγής μηνυμάτων που χρησιμοποιεί ένα request-reply
protocol. Οι clients στέλνουν στον Server ένα Request και έπειτα ο Server απαντάει
με ένα Response και η σύνδεση τους τερματίζει.
Χρησιμοποιήθηκε η τεχνολογία της RMI (Remote Method Invocation).

## Κλάσεις

Για την υλοποίηση του RMI δημιούργησα το interface MessagingInt, το οποίο κάνει
extend το interface Remote. Το αρχείο αυτό είναι κοινό για client/server.

Επιπλέον, δημιούργησα την υλοποίηση του interface στη μεριά του Server
(κλάση RemoteMessaging), η οποία κάνει extend την UnicastRemoteObject.

Δημιούργησα, επίσης, τις κλάσεις Account και Message, οι οποίες κάνουν implement
το interface java.io.Serializable.Το αρχεία αυτά είναι κοινά για client/server.

Τέλος, δημιούργησα τις κλάσεις Client και Server, οι οποίες υλοποιούν αντίστοιχα
τις υπηρεσίες του Client και του Server.

## MessagingInt

Το interface MessagingInt κάνει extend το interface Remote.

Στο interface αυτό έχουμε τις μεθόδους:

* int **create_account**(String username)
* ArrayList<Account> **show_accounts**(int authToken)
* boolean **send_message**(int authToken, String recipient, String message)
* ArrayList<Message> **show_inbox**(int authToken)
* String **read_message**(int authToken, int messageID)
* int **delete_message**(int authToken, int messageID)

οι οποίες αντιστοιχούν στις υπηρεσίες που προσφέρει σύστημα.

## RemoteMessaging

Η κλάση RemoteMessaging είναι η υλοποίηση του interface στη μεριά του Server και
κάνει extend την UnicastRemoteObject. Συγκεκριμένα, υλοποιεί τις μεθόδους που έχουν
οριστεί στο interface MessagingInt.

Η κλάση RemoteMessaging έχει τις ιδιότητες:
* int authTok=0
* int messID=0
* ArrayList<Account> accounts = new ArrayList<Account>()

Η ιδιότητα accounts είναι μια λίστα με όλα τα accounts που υπάρχουν στο σύστημα.

Κάθε φορά που γίνεται αίτημα για τη δημιουργία ενός νέου account, η μεταβλητή
authTok αυξάνεται κατά μία μονάδα και το account προστίθεται στη λίστα accounts.
Έτσι, ο πρώτος χρήστης που θα δημιουργήσει account, θα λάβει auth token == 1,
ο δεύτερος auth token == 2 κ.ο.κ.

Αντίστοιχα με τα μηνύματα, όταν πάει να σταλεί ένα μήνυμα, η μεταβλητή messID
αυξάνεται κατά μία μονάδα. Έτσι, το πρώτο μήνυμα που θα σταλεί, θα έχει messID == 1,
το δεύτερο messID == 2 κ.ο.κ.

## Account

Η κλάση Message είναι κοινή για client/server και κάνει implement το interface
java.io.Serializable. Έχει τις ιδιότητες:

* String **username**
* int **authToken**
* List<Message> **messageBox**

Η ιδιότητα messageBox είναι μια λίστα με όλα τα μηνύματα που έχει λάβει το
συγκεκριμένο account, δηλαδή το inbox του Account.

Πέρα από τον Constructor, που αρχικοποιεί τις παραπάνω
ιδιότητες, έχουμε τις μεθόδους:

* String **getUsername**(), η οποία επιστρέφει το username του account
* int **getAuthToken**(), η οποία επιστρέφει το auth token του account
* ArrayList<Message> **getInbox**(), η οποία επιστρέφει το inbox του account
* void **addMessage**(Message message), η οποία προσθέτει το μήνυμα message στο
  inbox του account
* String **getMessage**(int messID), η οποία επιστρέφει το μήνυμα με message ID ==
  messID από το inbox του account
* boolean **deleteMessage**(int messID), η οποία διαγράφει το μήνυμα με message ID ==
  messID από το inbox του account

## Message

Η κλάση Message είναι κοινή για client/server και κάνει implement το interface
java.io.Serializable. Έχει τις ιδιότητες:

* boolean **isRead**
* String **sender**
* String **receiver**
* String **body**
* int **messageID**

Επιπλέον, έχει έναν Constructor, ο οποίος αρχικοποιεί τις ιδιότητες.

## Server

Η κλάση Server υλοποιεί την υπηρεσία του Server. Συγκεκριμένα, λαμβάνει σαν όρισμα
το port, πάνω στο οποίο θα γίνει η σύνδεση κι έπειτα δημιουργείται το RMI Registry
πάνω στο port αυτό.

## Client

Η κλάση Client υλοποιεί την υπηρεσία του Client. Συγκεκριμένα, λαμβάνει σαν όρισμα
την ip και το port, πάνω στα οποία θα γίνει η σύνδεση και τα επιπλέον ορίσματα,
τα οποία είναι διαφορετικά ανάλογα με την υπηρεσία που θέλει να χρησιμοποιήσει ο
Client. Ανάλογα με την υπηρεσία που διαλέγει ο Client, υπάρχουν αντίστοιχες
συναρτήσεις που εμφανίζουν το αποτέλεσμα που ζητά ο Client στην οθόνη.

Συγκεκριμένα, έχουμε τις συναρτήσεις:

* void **printAuthToken**(int authToken), η οποία τυπώνει ένα auth token
* void **printAccounts**(ArrayList<Account> accountsList), η οποία τυπώνει μια λίστα
  με τα accounts που υπάρχουν στο σύστημα
* void **printSendMessage**(boolean ret), η οποία τυπώνει "OK" αν στάλθηκε με
  επιτυχία το μήνυμα
* void **printInbox**(ArrayList<Message> messageBox), η οποία τυπώνει μια λίστα
  με όλα τα μηνύματα που υπάρχουν στο messagebox του χρήστη
* void **printMessage**(String message), η οποία τυπώνει το μήνυμα που θέλει να
  διαβάσει ο χρήστης
* void **printDelMess**(int delMess), η οποία τυπώνει "OK" αν διαγράφηκε με επιτυχία
  το μήνυμα
