import React from 'react';
import { FormControl, FormLabel, Input, Stack, Button } from '@chakra-ui/react';
import axios from 'axios';
import { userUrl } from '@/constant';
import { useState } from 'react';

const AddAddress = ({ fetchRestaurants }) => {
  const [values, setValues] = useState({});

  const setValue = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const onAdd = () => {
    axios.post(userUrl + '/addresses', values);
    fetchRestaurants();
  };

  return (
    <Stack>
      <FormControl>
        <FormLabel>Şehir</FormLabel>
        <Input
          value={values.city}
          onChange={(e) => {
            setValue({ city: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>İlçe</FormLabel>
        <Input
          value={values.state}
          onChange={(e) => {
            setValue({ state: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Mahalle</FormLabel>
        <Input
          value={values.district}
          onChange={(e) => {
            setValue({ district: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Sokak</FormLabel>
        <Input
          value={values.street}
          onChange={(e) => {
            setValue({ street: e.target.value });
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
      <FormControl>
        <FormLabel>Kullanıcı Id</FormLabel>
        <Input
          value={values.userId}
          onChange={(e) => {
            setValue({ userId: e.target.value });
          }}
        />
      </FormControl>
      <Button onClick={onAdd}>Ekle</Button>
    </Stack>
  );
};

export default AddAddress;
