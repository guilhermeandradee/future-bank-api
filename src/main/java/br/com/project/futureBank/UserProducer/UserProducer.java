package br.com.project.futureBank.UserProducer;

import br.com.project.futureBank.entity.Account;
import br.com.project.futureBank.entity.DTOS.EmailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(Account account){
        var emailDTO = new EmailDTO();
        emailDTO.setUserId(account.getId());
        emailDTO.setEmailTo(account.getAdress());
        emailDTO.setSubject("Cadastro realizado com sucesso");
        emailDTO.setText("Seja bem vindo ao FutureBank! \nMuito obrigado pela sua inscrição, explore o meu github para visualizar mais projetos como esse! \nhttps://github.com/guilhermeandradee \nEmail para contato gui.andrade1510@gmail.com");

        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
    }
}
