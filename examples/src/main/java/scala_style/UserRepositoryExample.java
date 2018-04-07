package scala_style;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static scala_style.None.None;
import static scala_style.Option.Option;
import static scala_style.Some.Some;

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
        UserRepository.findById(2).foreach(u -> System.out.println(u.firstName));
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

        /* For comprehensions

        Now that you know that an Option can be treated as a collection and provides map, flatMap, filter and other
        methods you know from collections, you will probably already suspect that options can be used in for
        comprehensions. Often, this is the most readable way of working with options, especially if you have to chain
        a lot of map, flatMap and filter invocations. If it’s just a single map, that may often be preferrable, as it
        is a little less verbose.

        If we want to get the gender for a single user, we can apply the following for comprehension: */
        List<String> genderList = new ArrayList<>();
        for (User u : UserRepository.findById(1)) {
            for (String g : u.gender) {
                genderList.add(g);
            }
        }
        genderList.clear();

        /* If we wanted to retrieve the genders of all users that have specified it, we could iterate all users,
        and for each of them yield a gender, if it is defined: */
        for (User u : UserRepository.findAll()) {
            for (String g : u.gender) {
                genderList.add(g);
            }
        }
        /* Since we are effectively flat mapping, the result type is List[String], and the resulting list is
        List("male"), because gender is only defined for the first user. */


        /* Chaining options

        A good use case for this is finding a resource, when you have several different locations to search for it and
        an order of preference. In our example, we prefer the resource to be found in the config dir, so we call orElse
        on it, passing in an alternative option: */
        Option<Resource> resourceFromConfigDir = None();
        Option<Resource> resourceFromClasspath = Some(new Resource("I was found on the classpath"));
        Option<Resource> resource = resourceFromConfigDir.orElse(Resource.class, () -> resourceFromClasspath);
        /* This is usually a good fit if you want to chain more than just two options – if you simply want to provide
        a default value in case a given option is absent, the getOrElse method may be a better idea. */
    }

    public static final class UserRepository {
        private static final Map<Integer, User> users = new HashMap<>();

        static {
            users.put(1, new User(1, "John", "Doe", 32, Option("male")));
            users.put(2, new User(2, "Johanna", "Doe", 30, None()));
        }

        private UserRepository() {
        }

        static Option<User> findById(int id) {
            return Option(users.get(id));
        }

        static Collection<User> findAll() {
            return users.values();
        }

    }

    static final class User {
        final int id;
        final String firstName;
        final String lastName;
        final int age;
        final Option<String> gender;

        User(int id, String firstName, String lastName, int age, Option<String> gender) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
        }
    }

    static final class Resource {
        final String content;

        public Resource(String content) {
            this.content = content;
        }
    }
}
