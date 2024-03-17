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

const MutateReview = ({ fetchReviews }) => {
  const [values, setValues] = useState({});

  const setValue = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const onAdd = () => {
    axios.post(userUrl + '/reviews', values);
    fetchReviews();
  };
  const onUpdate = () => {
    axios.put(userUrl + `/reviews`, values);
    fetchReviews();
  };
  const onDelete = () => {
    axios.delete(userUrl + `/reviews/${values.id}`);
    fetchReviews();
  };

  return (
    <Stack>
      <FormControl>
        <FormLabel>Yorum</FormLabel>
        <Input
          value={values.comment}
          onChange={(e) => {
            setValue({ comment: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Puan</FormLabel>
        <Input
          value={values.rate}
          onChange={(e) => {
            setValue({ rate: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <FormLabel>Restoran Id</FormLabel>
        <Input
          value={values.restaurantId}
          onChange={(e) => {
            setValue({ restaurantId: e.target.value });
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

export default MutateReview;
