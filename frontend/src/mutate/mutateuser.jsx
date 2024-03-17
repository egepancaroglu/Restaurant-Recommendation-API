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
import { userUrl } from '@/constant';
import { useState } from 'react';

const MutateUser = ({ fetchUsers }) => {
  const [values, setValues] = useState({});

  const setValue = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const onAdd = () => {
    axios.post(userUrl + '/users', values);
    fetchUsers();
  };
  const onUpdate = () => {
    axios.put(userUrl + `/users`, values);
    fetchUsers();
  };
  const onDelete = () => {
    axios.delete(userUrl + `/users/${values.id}`);
    fetchUsers();
  };

  return (
    <Stack>
      <FormControl>
        <FormLabel>Kullanıcı adı</FormLabel>
        <Input
          value={values.userName}
          onChange={(e) => {
            setValue({ userName: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Ad</FormLabel>
        <Input
          value={values.firstName}
          onChange={(e) => {
            setValue({ firstName: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Soyad</FormLabel>
        <Input
          value={values.lastName}
          onChange={(e) => {
            setValue({ lastName: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Eposta</FormLabel>
        <Input
          value={values.email}
          onChange={(e) => {
            setValue({ email: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Doğum günü</FormLabel>
        <Input
          value={values.birthDate}
          onChange={(e) => {
            setValue({ birthDate: e.target.value });
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

export default MutateUser;
