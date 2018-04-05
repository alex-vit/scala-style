package scala_style;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static scala_style.None.None;
import static scala_style.Option.Option;

/**
 * Based on the incredible "The Neophyte's Guide to Scala Part 5: The Option Type" by Daniel Westheide
 * http://danielwestheide.com/blog/2012/12/19/the-neophytes-guide-to-scala-part-5-the-option-type.html
 */
public class UserRepositoryExample {

    public static void main(String[] args) {

        /* Working with optional values

        Now, if you received an instance of Option[User] from the UserRepository and need to do something with it,
        how do you do that? */
        Option<User> user1 = UserRepository.findById(1);

        /* One way would be to check if a value is present by means of the isDefined method of your option, and,
        if that is the case, get that value via its get method: */
        if (user1.isDefined()) {
            System.out.println(user1.get().firstName);
        } // will print "John"

        /* Providing a default value

        Very often, you want to work with a fallback or default value in case an optional value is absent.
        This use case is covered pretty well by the getOrElse method defined on Option: */
        User user = new User(2, "Johanna", "Doe", 30, None());
        System.out.printf("Gender: %s%n", user.gender.getOrElse(String.class, () -> "not specified"));

        /* Pattern matching [kind of]

        Option can be a Some or a None. Let’s rewrite the example above using class check: */
        if (user.gender instanceof Some<?>) {
            System.out.printf("Gender: %s%n", user.gender.get());
        } else {
            System.out.println("Gender: not specified");
        }
        // Or, if you want to remove the duplicated print statement:
        String gender = (user.gender instanceof Some<?>)
                ? user.gender.get()
                : "not specified";
        System.out.printf("Gender: %s%n", gender);

        /* Performing a side-effect if a value is present

        If you need to perform some side-effect only if a specific optional value is present, the foreach method comes
        in handy. */
        UserRepository.findById(2).forEach(u -> System.out.println(u.firstName));
        // The function passed to foreach will be called exactly once, if the Option is a Some, or never, if it is None.

        /* Mapping an option

        Just as you can map a List[A] to a List[B], you can map an Option[A] to an Option[B]. This means that if your
        instance of Option[A] is defined, i.e. it is Some[A], the result is Some[B], otherwise it is None.

        If you compare Option to List, None is the equivalent of an empty list: when you map an empty List[A], you get
        an empty List[B], and when you map an Option[A] that is None, you get an Option[B] that is None.

        Let’s get the age of an optional user: */
        Option<Integer> age = UserRepository.findById(1).map(u -> u.age); // age is Some(32)

        /* flatMap and options

        Let’s do the same for the gender: */
        Option<Option<String>> genderOption = UserRepository.findById(1).map(u -> u.gender);
        /* The type of the resulting gender is Option[Option[String]].
        Just like you can flatMap a List[List[A]] to a List[B], you can do the same for an Option[Option[A]]: */
        Option<String> gender1 = UserRepository.findById(1).flatMap(u -> u.gender); // gender is Some("male")
        Option<String> gender2 = UserRepository.findById(2).flatMap(u -> u.gender); // gender is None
        Option<String> gender3 = UserRepository.findById(3).flatMap(u -> u.gender); // gender is None
        /* The result type is now Option[String]. If the user is defined and its gender is defined, we get it as
        a flattened Some. If either the use or its gender is undefined, we get a None. */

        // TODO implement filtering and add the "Filtering an option" example
    }

    public static final class UserRepository {
        private static final Map<Integer, User> users = new HashMap<>();

        static {
            users.put(1, new User(1, "John", "Doe", 32, Option("male")));
            users.put(2, new User(2, "Johanna", "Doe", 30, None()));
        }

        private UserRepository() {
        }

        public static Option<User> findById(int id) {
            return Option(users.get(id));
        }

        public static Collection<User> findAll() {
            return users.values();
        }

    }

    public static final class User {
        public final int id;
        public final String firstName;
        public final String lastName;
        public final int age;
        public final Option<String> gender;

        public User(int id, String firstName, String lastName, int age, Option<String> gender) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
        }
    }
}
