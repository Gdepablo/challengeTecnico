package Backend.component;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;


public class UserToUserPrincipalConverter implements Converter<User, UserPrincipal> {


        @Override
        public UserPrincipal convert(MappingContext<User, UserPrincipal> context) {
                User source = context.getSource();
                UserPrincipal destination = new UserPrincipal();
                destination.setId(source.getId());
                destination.setUsername(source.getUsername());
                destination.setPassword(source.getPassword());
                return destination;
                // manually copy properties from source to destination, skipping the 'notes' property
        }
}
