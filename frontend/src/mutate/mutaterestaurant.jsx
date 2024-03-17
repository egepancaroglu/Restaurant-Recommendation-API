import React from 'react';
import {
  FormControl,
  FormLabel,
  Input,
  Stack,
  Button,
  Flex,
} from '@chakra-ui/react';
import axios from 'axios';
import { restaurantUrl } from '@/constant';
import { useState } from 'react';

const MutateRestaurant = ({ fetchRestaurants }) => {
  const [values, setValues] = useState({});

  const setValue = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const onAdd = () => {
    axios.post(restaurantUrl + '/restaurants', values);
    fetchRestaurants();
  };
  const onUpdate = () => {
    axios.put(restaurantUrl + `/restaurants`, values);
    fetchRestaurants();
  };
  const onDelete = () => {
    axios.delete(restaurantUrl + `/restaurants/${values.id}`);
    fetchRestaurants();
  };

  return (
    <Stack>
      <FormControl>
        <FormLabel>Ad</FormLabel>
        <Input
          value={values.name}
          onChange={(e) => {
            setValue({ name: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Konum</FormLabel>
        <Input
          value={values.location}
          onChange={(e) => {
            setValue({ location: e.target.value });
          }}
        />
      </FormControl>
      <Button onClick={onAdd}>Ekle</Button>
      <FormControl>
        <FormLabel>Id</FormLabel>
        <Input
          value={values.id}
          onChange={(e) => {
            setValue({ id: e.target.value });
          }}
        />
      </FormControl>
      <Flex>
        <Button onClick={onUpdate}>Güncelle</Button>
        <Button onClick={onDelete}>Sil</Button>
      </Flex>
    </Stack>
  );
};

export default MutateRestaurant;
