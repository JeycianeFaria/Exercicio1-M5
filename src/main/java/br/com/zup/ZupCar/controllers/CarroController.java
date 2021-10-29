package br.com.zup.ZupCar.controllers;

import br.com.zup.ZupCar.dtos.CarroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroController {

    private List<CarroDTO> concessionaria = new ArrayList<>();

    @GetMapping
    public List<CarroDTO> exibirCarros(){
        return concessionaria;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarCarro(@RequestBody CarroDTO carroDTO){
        concessionaria.add(carroDTO);
    }

    @GetMapping("/{nomeDoCarro}")
    public CarroDTO exibirCarro(@PathVariable String nomeDoCarro){

        for (CarroDTO referencia:concessionaria) {
            if (referencia.getModelo().equalsIgnoreCase(nomeDoCarro)){
                return referencia;
            }
        }

        /*Forma mais elegante
          Optional<CarroDTO> optionalCarroDTO = concessionaria.stream()
                .filter(carro -> carro.getModelo().equals(nomeDoCarro)).findFirst();

            if (optionalCarroDTO.isPresent()){
                 return optionalCarroDTO.get();
            }
        */


        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    //Atualizar um carro
    @PutMapping("/{nomeDoCarro}")
    public CarroDTO atualizarCarro(@PathVariable String nomeDoCarro, @RequestBody CarroDTO carroDTOAtualizado){

        CarroDTO carroAtualizado = null;

        for (CarroDTO referencia:concessionaria) {
            if (referencia.getModelo().equalsIgnoreCase(nomeDoCarro)){
                carroAtualizado = referencia;
            }
        }

        if (carroAtualizado == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        carroAtualizado.setCor(carroDTOAtualizado.getCor());
        carroAtualizado.setMotor(carroDTOAtualizado.getMotor());
        carroAtualizado.setAno(carroDTOAtualizado.getAno());

        return carroAtualizado;
    }


    //Deletar carro
    @DeleteMapping("/{nomeDoCarro}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCarro(@PathVariable String nomeDoCarro){
        CarroDTO carroDeletar = null;

        for (CarroDTO referencia:concessionaria) {
            if (referencia.getModelo().equalsIgnoreCase(nomeDoCarro)){
                carroDeletar = referencia;
            }
        }

        if(carroDeletar == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        concessionaria.remove(carroDeletar);
    }

}
