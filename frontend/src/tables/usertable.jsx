import { userUrl } from '@/constant';
import AddAddress from '@/mutate/addaddress';
import MutateUser from '@/mutate/mutateuser';
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Flex,
  Stack,
} from '@chakra-ui/react';
import axios from 'axios';
import { useCallback, useState, useEffect } from 'react';

const UserTable = () => {
  const [users, setUsers] = useState([]);

  const fetchUsers = useCallback(() => {
    axios.get(userUrl + '/users').then((res) => {
      setUsers(res.data.data);
    });
  }, []);

  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  return (
    <Flex gap={12}>
      <MutateUser fetchUsers={fetchUsers} />

      <TableContainer>
        <Table variant='simple'>
          <Thead>
            <Tr>
              <Th>Id</Th>
              <Th>Kullanıcı adı</Th>
              <Th>Ad</Th>
              <Th>Soyad</Th>
              <Th>Eposta</Th>
              <Th>Doğum günü</Th>
            </Tr>
          </Thead>
          <Tbody>
            {users.map((user) => {
              return (
                <Tr key={user.id}>
                  <Td>{user.id}</Td>
                  <Td>{user.userName}</Td>
                  <Td>{user.firstName}</Td>
                  <Td>{user.lastName}</Td>
                  <Td>{user.email}</Td>
                  <Td>{user.birthDate}</Td>
                </Tr>
              );
            })}
          </Tbody>
        </Table>
      </TableContainer>
      <AddAddress />
    </Flex>
  );
};

export default UserTable;
