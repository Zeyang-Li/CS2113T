//package seedu.address.testutil;
//
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.model.task.Task;
//
///**
// * A utility class for Person.
// */
//public class TaskUtil {
//
//    /**
//     * Returns an add command string for adding the {@code person}.
//     */
//    public static String getAddCommand(Task task) {
//        return AddCommand.COMMAND_WORD + " " + getTaskDetails(task);
//    }
//
//    /**
//     * Returns the part of command string for the given {@code person}'s details.
//     */
//    public static String getTaskDetails(Task task) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(PREFIX_NAME + task.getName().fullName + " ");
//        sb.append(PREFIX_STARTDATE + task.getStartDate().value + " ");
//        sb.append(PREFIX_STARTTIME + task.getStartTime().value + " ");
//        sb.append(PREFIX_ENDDATE + task.getEndDate().value + " ");
//        sb.append(PREFIX_ENDTIME + task.getEndTime().value + " ");
//        sb.append(PREFIX_DESCRIPTION + task.getDescription().value + " ");
//        task.getTags().stream().forEach(
//            s -> sb.append(PREFIX_TAG + s.tagName + " ")
//        );
//        return sb.toString();
//    }
//
//    /**
//     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
//
//    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
//        StringBuilder sb = new StringBuilder();
//        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
//        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
//        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
//        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
//        if (descriptor.getTags().isPresent()) {
//            Set<Tag> tags = descriptor.getTags().get();
//            if (tags.isEmpty()) {
//                sb.append(PREFIX_TAG);
//            } else {
//                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
//            }
//        }
//        return sb.toString();
//    }*/
//}
